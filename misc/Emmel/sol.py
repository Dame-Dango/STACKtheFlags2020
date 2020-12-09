from requests import post
from PIL import Image
color =[]

#for i in itertools.product(range(0,256),repeat=3):

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
