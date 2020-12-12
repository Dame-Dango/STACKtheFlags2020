---
layout: default
title: Welcome to Korovax Mobile!
parent: Mobile
permalink: /:path/
nav_order: 5
---
# Welcome to Korovax Mobile!

> Points: 1975 [2000]

## Description

> To be part of the Korovax team, do you really need to sign up to be a member?
> 
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
> 

## Solution
The challenge requires us to focus our attention on the `sg.gov.tech.ctf.mobile.User.AuthenticationActivity.java` at the start.

From how the `onCreate` method is coded, it appears there are two tabs, one for `Login`, and another for `Sign up`. We can first take a look at what `f.a.a.a.a.e.b.a()` does.

```java
    @Override // androidx.activity.ComponentActivity, a.h.d.d, a.k.a.d, a.b.k.d
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(8192, 8192);
        getWindow().setFlags(1024, 1024);
        getWindow().setSoftInputMode(32);
        setContentView(R.layout.user_authentication_activity);
        new f.a.a.a.a.e.b();
        f.a.a.a.a.e.b pathChecker = f.a.a.a.a.e.b.a();
        pathChecker.d(false);
        pathChecker.e(false);
        this.f2975b = (TabLayout) findViewById(R.id.tab_layout);
        this.f2976c = (ViewPager) findViewById(R.id.view_pager);
        this.f2977d = (FloatingActionButton) findViewById(R.id.fab_facebook);
        this.f2979f = (FloatingActionButton) findViewById(R.id.fab_twitter);
        this.f2978e = (FloatingActionButton) findViewById(R.id.fab_google);
        TabLayout tabLayout = this.f2975b;
        TabLayout.f w = tabLayout.w();
        w.o("Login");
        tabLayout.c(w);
        TabLayout tabLayout2 = this.f2975b;
        TabLayout.f w2 = tabLayout2.w();
        w2.o("Sign up");
        tabLayout2.c(w2);
        this.f2975b.setTabGravity(0);
        this.f2976c.setAdapter(new b(this, getSupportFragmentManager()));
        this.f2976c.g(new TabLayout.g(this.f2975b));
        // some animations between these two lines
        this.f2975b.b(new a());
    }
```

When we opened `f/a/a/a/a/e/b`, we noticed that the methods look normal and does not appear to have an indication of a flag. So we left and continued to analyze the `onCreate` method.

```java
package f.a.a.a.a.e;

public class b {

    /* renamed from: c  reason: collision with root package name */
    public static b f2895c;

    /* renamed from: a  reason: collision with root package name */
    public boolean f2896a;

    /* renamed from: b  reason: collision with root package name */
    public boolean f2897b;

    public static synchronized b a() {
        b bVar;
        synchronized (b.class) {
            if (f2895c == null) {
                f2895c = new b();
            }
            bVar = f2895c;
        }
        return bVar;
    }

    public void d(Boolean bool) {
        this.f2896a = bool.booleanValue();
    }

    public void e(Boolean bool) {
        this.f2897b = bool.booleanValue();
    }

    public Boolean b() {
        return Boolean.valueOf(this.f2896a);
    }

    public Boolean c() {
        return Boolean.valueOf(this.f2897b);
    }
}
```

Looking back at `onCreate`, it appears that they call `this.f2976c.setAdapter(new b(this, getSupportFragmentManager()));` to set the layouts for the tabs, so we should see what is the `b` class in this java file. We can see that at position 0 (which should be the login tab), it gets the layout from `f.a.a.a.a.e.a`. 

```java
    public class b extends n {
        public b(AuthenticationActivity authenticationActivity, i fm) {
            super(fm);
        }

        @Override // a.k.a.n
        public Fragment getItem(int position) {
            if (position == 0) {
                return new f.a.a.a.a.e.a();
            }
            if (position != 1) {
                return null;
            }
            return new c();
        }

        @Override // a.u.a.a
        public int getCount() {
            return 2;
        }
    }
```

Opening `f/a/a/a/a/e/a.java`, we can see a whole load of magic numbers scattered in the file...
```java
        // ...
        SQLiteDatabase db = dbhelper.getWritableDatabase(c.a.a.a.a(-212209524015184L)); // Line 38
        // ...
        public void onClick(View arg0) {
            Toast.makeText(this.f2889b.getContext(), c.a.a.a.a(-211582458789968L), 0).show();
        } // Line 73 - 75
        // ...
        public void onClick(View v) {
            String password = a.this.f2886c.getText().toString();
            if (password.contains(c.a.a.a.a(-211870221598800L))) {
                Toast.makeText(this.f2890b.getContext(), c.a.a.a.a(-211792912187472L), 0).show();
            }
            if (this.f2891c.e(c.a.a.a.a(-212132214603856L), password, this.f2891c.getReadableDatabase(c.a.a.a.a(-212140804538448L))).matches(c.a.a.a.a(-212093559898192L))) {
                c.a builder = new c.a(this.f2890b.getContext());
                View view = LayoutInflater.from(this.f2890b.getContext()).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText(c.a.a.a.a(-212016250486864L));
                ((TextView) view.findViewById(R.id.alert_detail)).setText(c.a.a.a.a(-211956120944720L));
                builder.h(c.a.a.a.a(-212407092510800L), new DialogInterface$OnClickListenerC0066a());
                builder.f(c.a.a.a.a(-212372732772432L), new DialogInterface$OnClickListenerC0067b());
                builder.k(view);
                builder.l();
                return;
            }
            Toast.makeText(this.f2890b.getContext(), c.a.a.a.a(-212329783099472L), 0).show();
        } // Line 91 - 108
        // ...
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(b.this.f2890b.getContext(), c.a.a.a.a(-211462199705680L), 0).show();
            } // Line 125 - 127
        // ...
```

So we decoded every single magic number using the code from [`mobile/Contact Us!`](../Contact%20Us!)... which gives us the flag!

```java
        // ...
        SQLiteDatabase db = dbhelper.getWritableDatabase("12345"); // Line 38
        // ...
        public void onClick(View arg0) {
            Toast.makeText(this.f2889b.getContext(), "You can't handle the truth!", 0).show();
        } // Line 73 - 75
        // ...
        public void onClick(View v) {
            String password = a.this.f2886c.getText().toString();
            if (password.contains("My_P@s5w0Rd_iS-L34k3d") {
                Toast.makeText(this.f2890b.getContext(), "Do you think it will be that easy? Muahaha", 0).show();
            }
            if (this.f2891c.e(user, password, this.f2891c.getReadableDatabase("12345").matches("My_P@s5w0Rd_iS-L34k3d") {
                c.a builder = new c.a(this.f2890b.getContext());
                View view = LayoutInflater.from(this.f2890b.getContext()).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText("Congrats!");
                ((TextView) view.findViewById(R.id.alert_detail)).setText("govtech-csg{15_tH1S_r3@L?}");
                builder.h("Proceed", new DialogInterface$OnClickListenerC0066a());
                builder.f("Close", new DialogInterface$OnClickListenerC0067b());
                builder.k(view);
                builder.l();
                return;
            }
            Toast.makeText(this.f2890b.getContext(), "Wrong username or password!", 0).show();
        } // Line 91 - 108
        // ...
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(b.this.f2890b.getContext(), "Do what you need to do here!", 0).show();
            } // Line 125 - 127
        // ...
```

## Flag
`govtech-csg{15_tH1S_r3@L?}`