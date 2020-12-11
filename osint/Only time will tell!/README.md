---
layout: default
title: Only time will tell!
permalink: /:path/
parent: Open Source Intelligence
nav_order: 1
---
# Only time will tell!

> Points: 772 [1000]

## Description

> This picture was taken sent to us! It seems like a bomb threat! Are you able to tell where and when this photo was taken? This will help the investigating officers to narrow down their search! All we can tell is that it's taken during the day!
>
> If you think that it's 7.24pm in which the photo was taken. Please take the associated 2 hour block. This will be 1900-2100. If you think it is 10.11am, it will be 1000-1200.
>
> Flag Example: govtech-csg{1.401146_103.927020_1990:12:30_2000-2200}
> Use this [calculator](https://www.pgc.umn.edu/apps/convert/)!
>
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
>
> Flag Format: govtech-csg{lat_long_date_[two hour block format]}
> 
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award
> - Prerequisite for Mastery Award - Intelligence Officer
>
> Addendum:
> - The amount of decimal places required is the same as shown in the example given.
> - CLI tool to get something before you convert it with the calculator. 

## Solution

![](osint-challenge-6.jpg)

Running `exiftool`, I extracted the given picture's metadata and was able to get the GPS latitude and longtitude.

```bash
$ exiftool osint-challenge-6.jpg
...
GPS Latitude                    : 1 deg 17' 11.93" N
GPS Longitude                   : 103 deg 50' 48.61" E
GPS Position                    : 1 deg 17' 11.93" N, 103 deg 50' 48.61" E
```

I then used the given website and got `1.286647` as the latitude and `103.846836` as the longtitude. 

Next, we will need to guess the time that this picture was taken. From the point-of-view that this picture was taken, the camera was facing the west. And since the shadows were pointing towards the camera, it must in the afternoon. We tried a few time intervals before getting the right time slot: `1500-1700`.

## Flag
`govtech-csg{1.286647_103.846836_2020:10:25_1500-1700}`