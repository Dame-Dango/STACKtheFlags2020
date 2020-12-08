# What is he working on? Some high value project?

> Points: 818 [1000]

## Description

> The lead Smart Nation engineer is missing! He has not responded to our calls for 3 days and is suspected to be kidnapped! Can you find out some of the projects he has been working on? Perhaps this will give us some insights on why he was kidnapped…maybe some high-value projects! This is one of the latest work, maybe it serves as a good starting point to start hunting.
>
> Flag is the repository name!
>
> [Developer's Portal - STACK the Flags](https://www.developer.tech.gov.sg/communities/events/stack-the-flags-2020)
>
> Note: Just this page only! Only stack-the-flags-2020 page have the clues to help you proceed. Please do not perform any scanning activities on www.developer.tech.gov.sg. This is not part of the challenge scope!
>
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award

## Solution

Viewing the source code of the page, we see a comment containing an online handle `@joshhky`.

```html
...
<a href="https://ctf.tech.gov.sg/">
  <h3 style="text-align: center;">Check out STACK the Flags here!</h3>
</a>

<!-- Will fork to our gitlab - @joshhky -->

    <p>
      <em>
        Last updated 04 December 2020
      </em>
    </p>
  </div>
</div>
...
```

Using [`sherlock`](https://github.com/sherlock-project/sherlock), I was able to find his [`Gitlab`](https://gitlab.com/joshhky) profile.

```bash
$ python3 sherlock "joshhky"                          
[*] Checking username joshhky on:
...
[+] GitLab: https://gitlab.com/joshhky
...
```

His profile also listed a suspicious [repository](https://gitlab.com/korovax/korovax-employee-wiki) that he had recently worked on. Viewing the latest [commit](https://gitlab.com/korovax/korovax-employee-wiki/-/commit/1173a1ae90edc05618850f73f02569e657af95a1) that he performed (which might be the commit that led to his kidnap), we see that he had replace the contents of the `README.md` file with:

```
Extracted wiki template from GitLab pages examples.
This POC will be complementary to the whole suite of offerings which KoroVax will use internally.
Todo:
- The employee wiki will list what each employee is responsible for, eg, Josh will be in charge of the krs-admin-portal
- Please note that not all repository should be made public, relevant ones with business data should be made private
POC Site: https://korovax.gitlab.io/korovax-employee-wiki
```

The `krs-admin-portal` seemed like a high value project and it turned out to be the flag.

## Flag
`govtech-csg{krs-admin-portal}`