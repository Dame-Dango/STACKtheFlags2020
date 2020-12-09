# Contact Us!

> Points: 959 [1000]

## Description

> Looks like Korovax has exposed some development comment lines accidentally. Can you get one of the secrets through this mistake?
> 
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
> 

## Solution
1. The challenge requires us to focus our attention on the `sg.gov.tech.ctf.mobile.Contact.ContactForm.java` 
2. Looking at the source code, we can see that they called a native function, but as you scroll down, we can see that there is a `"Congrats!"` string printed.
```java 
                c.a builder = new c.a(ContactForm.this);
                View view = LayoutInflater.from(ContactForm.this).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText("Congrats!");
                ((TextView) view.findViewById(R.id.alert_detail)).setText(new f.a.a.a.a.b.a().a());
                builder.h("Proceed", new DialogInterface$OnClickListenerC0070a());
                builder.f("Close", new b());
```
3. Focusing on the line after the `"Congrats!"`, the application sets a text by calling `new f.a.a.a.a.b.a().a()`, which leads to another method call at `c.a.a.a.a(-222036409188432L)`.
```java
package f.a.a.a.a.b;

public class a {
    public String a() {
        return c.a.a.a.a(-222036409188432L);
    }
}
```
4. Opening [`c/a/a/a.java`](a.java) will show a ridiculously long string, along with some decoding functions, calling the other classes in the same package. 
5. As the code looks needlessly complicated to reverse, we have decided to just copy the code wholesale, and compile a local copy, so that we can decode any of the strings based on a provided number.

[Main.java](Main.java)
```java
public class Main {
  public static void main(String[] args) {
    System.out.println(a.a(Long.parseLong(args[0])));
  }
}
```

6. Running the following will compile the Java code and run the main function, and volia we have the flag!
```sh
javac -encoding UTF-8 -d ./build *.java
cd build
java Main -222036409188432 # Derived from Step 3
# Output: govtech-csg{Ez_F1aG_4_wARmUp!}
```

## Flag
`govtech-csg{Ez_F1aG_4_wARmUp!}`