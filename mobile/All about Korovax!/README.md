# All about Korovax!

> Points: 994 [1000]

## Description

> As a user and member of Korovax mobile, you will be treated with a lot of information about COViD and a few in-app functions that should help you understand more about COViD and Korovax! Members should be glad that they even have a notepad in there, to create notes as they learn more about Korovax's mission!
> 
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
> 
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award

## Solution
1. We can focus on `sg.gov.tech.ctf.mobile.User.ViewActivity`. From the file, we can see an `onClick` method that has some interesting code. It checks the function `ViewActivity.this.a()`. They seem to set a string with `R.string.test`.
```java
        public void onClick(View v) {
            if (ViewActivity.this.a() == 1720543) {
                c.a builder = new c.a(ViewActivity.this);
                View view = LayoutInflater.from(ViewActivity.this).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText("Congrats!");
                ((TextView) view.findViewById(R.id.alert_detail)).setText(R.string.test);
                f.a.a.a.a.e.b.a().d(true);
                builder.h("Proceed", new DialogInterface$OnClickListenerC0075a());
                builder.f("Close", new b());
                builder.k(view);
                builder.l();
                return;
            }
            Toast.makeText(ViewActivity.this, "Something's happening...", 0).show();
            Toast.makeText(ViewActivity.this, "Maybe not.", 0).show();
        }
```
2. Based on how Android handles resources, we can see that the `R.string.test` is actually a string that can be found in the `strings.xml` in `resources/res/values`.
```xml
    <string name="test">Z292dGVjaC1jc2d7Y0xJY0tfTWVfTDNBRDVfMl9uMFdoM3IzfQ==</string>
```
3. Decoding the base64 string will give us the flag!
```
govtech-csg{cLIcK_Me_L3AD5_2_n0Wh3r3}
```

## Flag
`govtech-csg{cLIcK_Me_L3AD5_2_n0Wh3r3}`