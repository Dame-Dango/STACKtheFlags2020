---
layout: default
title: True or false?
parent: Mobile
permalink: /:path/
nav_order: 2
---
# True or false?

> Points: 1985 [2000]

## Description

> True or false, we can log in as admin easily.
> 
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
> 
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award
> - Prerequisite for Mastery Award - Mobile Ace

## Solution
1. Similar to how we solved [mobile/Welcome to Korovax Mobile!](../Welcome%20to%20Korovax%20Mobile!), we first will take a look at the `sg.gov.tech.ctf.mobile.Admin.AdminAuthenticationActivity.java`. From the `onCreate` method, we can see that they are also setting the same tab layouts as `sg.gov.tech.ctf.mobile.User.AuthenticationActivity.java`.
```java
    @Override // androidx.activity.ComponentActivity, a.h.d.d, a.k.a.d, a.b.k.d
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(8192, 8192);
        getWindow().setFlags(1024, 1024);
        getWindow().setSoftInputMode(32);
        setContentView(R.layout.admin_authentication_activity);
        this.f2923b = (TabLayout) findViewById(R.id.tab_layout);
        this.f2924c = (ViewPager) findViewById(R.id.view_pager);
        this.f2925d = (FloatingActionButton) findViewById(R.id.fab_facebook);
        this.f2927f = (FloatingActionButton) findViewById(R.id.fab_twitter);
        this.f2926e = (FloatingActionButton) findViewById(R.id.fab_google);
        TabLayout tabLayout = this.f2923b;
        TabLayout.f w = tabLayout.w();
        w.o("Login");
        tabLayout.c(w);
        TabLayout tabLayout2 = this.f2923b;
        TabLayout.f w2 = tabLayout2.w();
        w2.o("Sign up");
        tabLayout2.c(w2);
        this.f2923b.setTabGravity(0);
        this.f2924c.setAdapter(new b(this, getSupportFragmentManager()));
        this.f2924c.g(new TabLayout.g(this.f2923b));
        // Truncated some animation stuff between these two lines
        this.f2923b.b(new a());
    }
```
2. We can first focus on the `this.f2924c.setAdapter(new b(this, getSupportFragmentManager()))` to see what are the layout contents in each tab. As we can see, it is also creating a new object in `f.a.a.a.a.a.a`.
```java
    public class b extends n {
        public b(AdminAuthenticationActivity adminAuthenticationActivity, i fm) {
            super(fm);
        }

        @Override // a.k.a.n
        public Fragment getItem(int position) {
            if (position == 0) {
                return new f.a.a.a.a.a.a();
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
3. When we open `f.a.a.a.a.a.a`, we see another whole lot of magic numbers...
```java
// ...
// Line 39 - 45
        SQLiteDatabase db = dbHelperAdmin.getWritableDatabase(c.a.a.a.a(-221070041546832L));
        if (!dbHelperAdmin.b(c.a.a.a.a(-221323444617296L), db)) {
            dbHelperAdmin.a(db);
        }
        EditText editText = (EditText) root.findViewById(R.id.username_input);
        this.f2850b = editText;
        editText.setText(c.a.a.a.a(-221280494944336L));
// ...
// Line 76 - 78
        public void onClick(View arg0) {
            Toast.makeText(this.f2854b.getContext(), c.a.a.a.a(-220168098414672L), 0).show();
        }
// ...
// Line 94 - 111
        public void onClick(View v) {
            if (a.this.f2851c.getText().toString().contains(c.a.a.a.a(-220945487495248L))) {
                c.a builder = new c.a(this.f2855b.getContext());
                View view = LayoutInflater.from(this.f2855b.getContext()).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText(c.a.a.a.a(-220786573705296L));
                ((TextView) view.findViewById(R.id.alert_detail)).setText(c.a.a.a.a(-220760803901520L));
                builder.h(c.a.a.a.a(-221155940892752L), new DialogInterface$OnClickListenerC0061a());
                builder.f(c.a.a.a.a(-221121581154384L), new DialogInterface$OnClickListenerC0062b());
                builder.k(view);
                builder.l();
                return;
            }
            try {
                new Thread(new c(new Handler())).start();
            } catch (Exception e2) {
                Toast.makeText(this.f2855b.getContext(), c.a.a.a.a(-221078631481424L), 0).show();
            }
        }
// ...
// Line 128 - 130
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(b.this.f2855b.getContext(), c.a.a.a.a(-220249702793296L), 0).show();
            }
// ...
// Line 147 - 149
                public void run() {
                    Toast.makeText(b.this.f2855b.getContext(), Boolean.valueOf(b.this.f2856c.d(a.this.b(a.this.f2851c.getText().toString()), b.this.f2856c.getWritableDatabase(c.a.a.a.a(-220692084424784L)))).toString(), 0).show();
                }
// ...
// Line 158 - 160
    public final String b(String query) {
        return query.replaceAll(c.a.a.a.a(-221748646379600L), c.a.a.a.a(-221709991673936L)).replaceAll(c.a.a.a.a(-221718581608528L), c.a.a.a.a(-221671336968272L)).replaceAll(c.a.a.a.a(-221645567164496L), c.a.a.a.a(-221615502393424L)).replaceAll(c.a.a.a.a(-221589732589648L), c.a.a.a.a(-221563962785872L)).replaceAll(c.a.a.a.a(-222087948795984L), c.a.a.a.a(-222062178992208L));
    }
```
4. ... so we decoded every single magic number using the code from [`mobile/Contact Us!`](../Contact%20Us!)... which gives us the flag!
```java
// ...
// Line 39 - 45
        SQLiteDatabase db = dbHelperAdmin.getWritableDatabase("kjhfkafkhlasfhsldfkasdfkasjdfbmnbzbc124914814298iewhrohfjdjksabvbdvs");
        if (!dbHelperAdmin.b("admin", db)) {
            dbHelperAdmin.a(db);
        }
        EditText editText = (EditText) root.findViewById(R.id.username_input);
        this.f2850b = editText;
        editText.setText("Find the password!");
// ...
// Line 76 - 78
        public void onClick(View arg0) {
            Toast.makeText(this.f2854b.getContext(), "Hint: 32 characters with special characters and spaces. TABLE name: Users, password column: you know it! :) ", 0).show();
        }
// ...
// Line 94 - 111
        public void onClick(View v) {
            if (a.this.f2851c.getText().toString().contains("My_P@s5w0Rd_iS-L34k3d_AG41n! T_T") {
                c.a builder = new c.a(this.f2855b.getContext());
                View view = LayoutInflater.from(this.f2855b.getContext()).inflate(R.layout.custom_alert, (ViewGroup) null);
                ((TextView) view.findViewById(R.id.title)).setText("Congrats!");
                ((TextView) view.findViewById(R.id.alert_detail)).setText("govtech-csg{I_RLy_W3nT_1n_BL1nD...}");
                builder.h("-221155940892752", new DialogInterface$OnClickListenerC0061a());
                builder.f("Close", new DialogInterface$OnClickListenerC0062b());
                builder.k(view);
                builder.l();
                return;
            }
            try {
                new Thread(new c(new Handler())).start();
            } catch (Exception e2) {
                Toast.makeText(this.f2855b.getContext(),  "False", 0).show();
            }
        }
// ...
// Line 128 - 130
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(b.this.f2855b.getContext(), "Do what you need to do here!", 0).show();
            }
// ...
// Line 147 - 149
                public void run() {
                    Toast.makeText(b.this.f2855b.getContext(), Boolean.valueOf(b.this.f2856c.d(a.this.b(a.this.f2851c.getText().toString()), b.this.f2856c.getWritableDatabase("kjhfkafkhlasfhsldfkasdfkasjdfbmnbzbc124914814298iewhrohfjdjksabvbdvs").toString(), 0).show();
                }
// ...
// Line 158 - 160
    public final String b(String query) {
        return query.replaceAll("(?i)drop", "").replaceAll("(?i)delete", "").replaceAll("(?i)insert", "").replaceAll("(?i)while", "").replaceAll("(?i)sleep", "");
    }
```

## Flag
`govtech-csg{I_RLy_W3nT_1n_BL1nD...}`