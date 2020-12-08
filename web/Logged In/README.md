# Logged In

### Points: 976 [1000]

## Description 
> It looks like COViD's mobile application is connecting to this API! Fortunately, our agents stole part of the source code. Can you find a way to log in?
>
> [API Server](http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41061/)
>
> Please view this [Document](https://docs.google.com/document/d/1GrQ6znlN2Z0tu_uAPAs1qrn6by24I51mq8RIIHmFGDU/edit?usp=sharing) for download instructions.
>
> ZIP File Password: web-challenge-6
>
> Note: Wondering what the second flag is about? Maybe check for a MOBILE Network?
>
> This challenge:
> - Unlocks other challenge(s)
> - Is eligible for Awesome Write-ups Award

## Solution

Taking a look at the given source code, we find in `app.js` that the api route is at `/api`, and in `routes/api.js` that there is a `/login` route. Putting these two together, the api login route is at `/api/login`.

The file `seeders/20201023021100-user.js` reveals that there are four user accounts. `bob_minion`, `kevin_minion`, `stuart_minion` and `gru_felonius`.

Upon further digging, we find in `/helpers/initLocalStrategy.js` that the only acceptable login user is `gru_felonius`.

An attempt at a basic POST request returned an invalid paramterers error. This is to be expected since no username or password was supplied.

```bash
$ curl -X POST http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41061/api/login
{"error":"Invalid parameters: username, password"}
```

We know from `middlewares/validators.js` that the server checks for the parameters `username` and `password`. The next attempt was a POST request with the credentials as form parameters.

```bash
$ curl -X POST -d "username=gru_felonius&password=password" http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41061/api/login
{"error":"Invalid credentials"}
```

This time it returned an invalid credentials error which means that the credentials have been properly processed. Since the nodejs app was using brypt, I found a [recent bug in bcrypt](https://portswigger.net/daily-swig/bcrypt-hashing-library-bug-leaves-node-js-applications-open-to-brute-force-attacks) that may be exploited to log in. However that did not yield any results.

The final guess was to leave the password parameter empty and it worked!

```bash
$ curl -X POST -d "username=gru_felonius&password=" http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41061/api/login
{"flagOne":"govtech-csg{m!sS1nG_cR3DeN+!@1s}","encryptedFlagTwo":"717f4cda287d40c47e7b50cb772b4def5a415387257510d1"}
```

## Flag
`govtech-csg{m!sS1nG_cR3DeN+!@1s}`
