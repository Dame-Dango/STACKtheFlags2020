# Find the leaking bucket!

> Points: 992 [1000]

## Description

> It was made known to us that agents of COViD are exfiltrating data to a hidden S3 bucket in AWS! We do not know the bucket name! One tip from our experienced officers is that bucket naming often uses common words related to the company’s business.
>
> Do what you can! Find that hidden S3 bucket (in the format “word1-word2-s4fet3ch”) and find out what was exfiltrated!
>
> [Company Website](https://d1ynvzedp0o7ys.cloudfront.net/)
>
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award
> - Prerequisite for Mastery Award - Cloud Guru
>
> Please ignore these troll buckets:
> - s3://intelligent-intelligent-s4fet3ch/
> - s3://steve-jobs-s4fet4ch/
> - s3://mobile-cybersecurity-s4fet3ch/

## Solution

![](website.png)

Taking a look at the page, we get a bunch of words that might possibly make up the name of the bucket that we are looking for. Using a bit of `regex` magic, I was able to scrape them out:

```bash
$ cat wordlist.txt
wireless
digital
parking
data
information
architecture
wifi
smartcity
computer
efficiency
technology
payment
ai
fintech
analytics
applications
internet
cybersecurity
iot
innovation
systems
knowledge
communication
mobile
intelligent
```

Using `ffuf`, I used the above list but was not able to find the bucket.

```bash
$ ffuf  -w wordlist.txt:WORD1 -w wordlist.txt:WORD2 -u "https://WORD1-WORD2-s4fet3ch.s3.amazonaws.com/" -c -v   

        /'___\  /'___\           /'___\       
       /\ \__/ /\ \__/  __  __  /\ \__/       
       \ \ ,__\\ \ ,__\/\ \/\ \ \ \ ,__\      
        \ \ \_/ \ \ \_/\ \ \_\ \ \ \ \_/      
         \ \_\   \ \_\  \ \____/  \ \_\       
          \/_/    \/_/   \/___/    \/_/       

       v1.0.2
________________________________________________

 :: Method           : GET
 :: URL              : https://WORD1-WORD2-s4fet3ch.s3.amazonaws.com/
 :: Follow redirects : false
 :: Calibration      : false
 :: Timeout          : 10
 :: Threads          : 40
 :: Matcher          : Response status: 200,204,301,302,307,401,403
________________________________________________
```

Here I figured that I needed more words in my wordlist and decided to add in the words found in the quote of Steve Jobs. 

```bash
$ cat wordlist.txt
the
people
who
are
crazy
enough
to
think
they
can
change
world
are
ones
who
do
...
```

Using the updated wordlist, we tried again. I was also afraid that AWS might block my IP address so I decided to add a delay to my requests.  

```bash
$ ffuf -p 0.9-1.1  -w wordlist.txt:WORD1 -w wordlist.txt:WORD2 -u "https://WORD1-WORD2-s4fet3ch.s3.amazonaws.com/" -c -v       

        /'___\  /'___\           /'___\       
       /\ \__/ /\ \__/  __  __  /\ \__/       
       \ \ ,__\\ \ ,__\/\ \/\ \ \ \ ,__\      
        \ \ \_/ \ \ \_/\ \ \_\ \ \ \ \_/      
         \ \_\   \ \_\  \ \____/  \ \_\       
          \/_/    \/_/   \/___/    \/_/       

       v1.0.2
________________________________________________

 :: Method           : GET
 :: URL              : https://WORD1-WORD2-s4fet3ch.s3.amazonaws.com/
 :: Follow redirects : false
 :: Calibration      : false
 :: Timeout          : 10
 :: Threads          : 40
 :: Delay            : 0.90 - 1.10 seconds
 :: Matcher          : Response status: 200,204,301,302,307,401,403
________________________________________________

[Status: 200, Size: 465, Words: 4, Lines: 2]                                                                                          
| URL | https://think-innovation-s4fet3ch.s3.amazonaws.com/
    * WORD1: think
    * WORD2: innovation
```

The bucket name is `think-innovation-s4fet3ch`! Using `awscli`, I was able to list and download the contents of the bucket.

```bash
# Listing bucket contents
$ aws s3 ls s3://think-innovation-s4fet3ch/ --no-sign-request
2020-11-17 10:59:54     273804 secret-files.zip

# Downloading secret-files.zip
$ aws s3 cp s3://think-innovation-s4fet3ch/secret-files.zip . --no-sign-request
download: s3://think-innovation-s4fet3ch/secret-files.zip to ./secret-files.zip
```

If we attempt to unzip it, it will prompt us for a password.

```
$ unzip secret-files.zip
Archive:  secret-files.zip
[secret-files.zip] flag.txt password: 
   skipping: flag.txt                incorrect password
   skipping: STACK the Flags Consent and Indemnity Form.docx  incorrect password
```

I instinctively used `john`, `zip2john` and the `rockyou.txt` wordlist to crack it but it was no avail. Then my friend suggested to perform a known plaintext attack on ZIP files. 

Using [pkcrack](https://github.com/keyunluo/pkcrack) and the "STACK the Flags Consent and Indemnity Form.docx" which was found on [GovTech's CTF website](https://ctf.tech.gov.sg/files/STACK%20the%20Flags%20Consent%20and%20Indemnity%20Form.docx), I was able to extract the `flag.txt` in the password-protected zip file.

```bash
# Creating the plaintext zip file
$ zip indemnity.zip "STACK the Flags Consent and Indemnity Form.docx"
  adding: STACK the Flags Consent and Indemnity Form.docx (deflated 1%)

# Performing known plaintext attack
$ pkcrack/bin/pkcrack -c "STACK the Flags Consent and Indemnity Form.docx" -p "STACK the Flags Consent and Indemnity Form.docx" -C secret-files.zip -P indemnity.zip -d output.zip
Files read. Starting stage 1 on Tue Dec  8 05:20:12 2020
Generating 1st generation of possible key2_273321 values...done.
Found 4194304 possible key2-values.
Now we\'re trying to reduce these...
Lowest number: 997 values at offset 265129
...
Done. Left with 94 possible Values. bestOffset is 247097.
Stage 1 completed. Starting stage 2 on Tue Dec  8 05:20:31 2020
Ta-daaaaa! key0=f5af793b, key1=6d3ea7ba, key2=9b71082d
Probabilistic test succeeded for 26229 bytes.
Ta-daaaaa! key0=f5af793b, key1=6d3ea7ba, key2=9b71082d
Probabilistic test succeeded for 26229 bytes.
Ta-daaaaa! key0=f5af793b, key1=6d3ea7ba, key2=9b71082d
Probabilistic test succeeded for 26229 bytes.
Stage 2 completed. Starting zipdecrypt on Tue Dec  8 05:20:34 2020
Decrypting flag.txt (1918da1aa13583f007af7db7)... OK!
Decrypting STACK the Flags Consent and Indemnity Form.docx (336ab103cd78d1b9756efc91)... OK!
Finished on Tue Dec  8 05:20:34 2020
```

And finally we can get our flag.

```bash 
$ unzip output.zip
Archive:  output.zip
 extracting: flag.txt                
 inflating: STACK the Flags Consent and Indemnity Form.docx
$ cat flag.txt
govtech-csg{EnCrYpT!0n_D0e$_NoT_M3@n_Y0u_aR3_s4f3} 
```

## Flag
`govtech-csg{EnCrYpT!0n_D0e$_NoT_M3@n_Y0u_aR3_s4f3}`