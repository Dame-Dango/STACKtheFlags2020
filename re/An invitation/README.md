# An invitation

> Points: 986 [1000]

## Description

>We want you to be a member of the Cyber Defense Group! Your invitation has been encoded to avoid being detected by COViD's sensors. Decipher the invitation and join in the fight!
>
>Please view this Document for download instructions.  
>
>This challenge:
>- Unlocks other challenge(s)
>- Is eligible for Awesome Write-ups Award

## Solution
This challenge was an interesting javascript reversing challenge that look somewhat daunting but could be solved rather easily.

Opening the index.html gave us a blank html page which was a little puzzling. Checking the console showed us this error'
`invite.js:1 Uncaught ReferenceError: gl is not defined
    at invite.js:1`  

Taking a look at the `invite.js` we are greeted with a nasty obfuscated code
Let's try deobfuscating it with https://lelinhtinh.github.io/de4js/, the result can be found in [invite_formatted.js](invite_formatted.js)  

At this point we started going through the code manually and slow identified portions of the code and found the piece of code causing the error mentioned above

```javascript
gl[_0x3f3a[11]](function (_0x92e4x5, _0x92e4x8, _0x92e4x9, _0x92e4xa, _0x92e4xb, _0x92e4xc) {
    var _0x92e4xd = _0x3db8;
    _0x92e4xb = function (_0x92e4xe) {
        var _0x92e4xf = _0x3db8;
        return (_0x92e4xe < _0x92e4x8 ? _0x3f3a[23] : _0x92e4xb(parseInt(_0x92e4xe / _0x92e4x8))) + ((_0x92e4xe = _0x92e4xe % _0x92e4x8) > 0x23 ? String[_0x3f3a[24]](_0x92e4xe + 0x1d) : _0x92e4xe[_0x92e4xf(_0x3f3a[25])](0x24))
    };
    if (!_0x3f3a[23][_0x92e4xd(_0x3f3a[26])](/^/, String)) {
        while (_0x92e4x9--) {
            _0x92e4xc[_0x92e4xb(_0x92e4x9)] = _0x92e4xa[_0x92e4x9] || _0x92e4xb(_0x92e4x9)
        };
        _0x92e4xa = [function (_0x92e4x10) {
            return _0x92e4xc[_0x92e4x10]
        }], _0x92e4xb = function () {
            var _0x92e4x11 = _0x92e4xd;
            return _0x92e4x11(_0x3f3a[27])
        }, _0x92e4x9 = 0x1
    };
    while (_0x92e4x9--) {
        _0x92e4xa[_0x92e4x9] && (_0x92e4x5 = _0x92e4x5[_0x92e4xd(_0x3f3a[26])](new RegExp(_0x3f3a[28] + _0x92e4xb(_0x92e4x9) + _0x3f3a[28], _0x3f3a[29]), _0x92e4xa[_0x92e4x9]))
    };
    return _0x92e4x5
}(_0x27631a(_0x3f3a[19]), 0x3e, 0x7c, _0x27631a(_0x3f3a[22])[_0x3f3a[21]](_0x3f3a[20]), 0x0, {}))
```

At this point we thought that this code be possible to execute.  
We decided to fire up nodejs and run the code (Of course with the other required functions not just this code) and interestingly we got more code

```javascript
`x=[0,0,0];const compare=(a,b)=>{let s='';for(let i=0;i<Math.max(a.length,b.length);i++){s+=String.fromCharCode((a.charCodeAt(i)||0)^(b.charCodeAt(i)||0))}return s};if(location.protocol=='file:'){x[0]=23}else{x[0]=57}if(compare(window.location.hostname,"you're invited!!!")==unescape("%1E%00%03S%17%06HD%0D%02%0FZ%09%0BB@M")){x[1]=88}else{x[1]=31}function yyy(){var uuu=false;var zzz=new Image();Object.defineProperty(zzz,'id',{get:function(){uuu=true;x[2]=54}});requestAnimationFrame(function X(){uuu=false;console.log("%c",zzz);if(!uuu){x[2]=98}})};yyy();function ooo(seed){var m=0xff;var a=11;var c=17;var z=seed||3;return function(){z=(a*z+c)%m;return z}}function iii(eee){ttt=eee[0]<<16|eee[1]<<8|eee[2];rrr=ooo(ttt);ggg=window.location.pathname.slice(1);hhh="";for(i=0;i<ggg.length;i++){hhh+=String.fromCharCode(ggg.charCodeAt(i)-1)}vvv=atob("3V3jYanBpfDq5QAb7OMCcT//k/leaHVWaWLfhj4=");mmm="";if(hhh.slice(0,2)=="go"&&hhh.charCodeAt(2)==118&&hhh.indexOf('ech-c')==4){for(i=0;i<vvv.length;i++){mmm+=String.fromCharCode(vvv.charCodeAt(i)^rrr())}alert("Thank you for accepting the invite!\\n"+hhh+mmm)}}for(a=0;a!=1000;a++){debugger}$('.custom1').catLED({type:'custom',color:'#FF0000',background_color:'#e0e0e0',size:10,rounded:5,font_type:4,value:" YOU'RE INVITED! "});$('.custom2').catLED({type:'custom',color:'#FF0000',background_color:'#e0e0e0',size:10,rounded:5,font_type:4,value:"                 "});$('.custom3').catLED({type:'custom',color:'#FF0000',background_color:'#e0e0e0',size:10,rounded:5,font_type:4,value:"   WE WANT YOU!  "});setTimeout(function(){iii(x)},2000);`
```
Once again we formatted the deobfuscated the code with https://lelinhtinh.github.io/de4js/ and was able to obtain the code

```javascript
x = [0, 0, 0];
const compare = (a, b) => {
    let s = '';
    for (let i = 0; i < Math.max(a.length, b.length); i++) {
        s += String.fromCharCode((a.charCodeAt(i) || 0) ^ (b.charCodeAt(i) || 0))
    }
    return s
};
if (location.protocol == 'file:') {
    x[0] = 23
} else {
    x[0] = 57
}
if (compare(window.location.hostname, "you're invited!!!") == unescape("%1E%00%03S%17%06HD%0D%02%0FZ%09%0BB@M")) {
    x[1] = 88
} else {
    x[1] = 31
}

function yyy() {
    var uuu = false;
    var zzz = new Image();
    Object.defineProperty(zzz, 'id', {
        get: function () {
            uuu = true;
            x[2] = 54
        }
    });
    requestAnimationFrame(function X() {
        uuu = false;
        console.log("%c", zzz);
        if (!uuu) {
            x[2] = 98
        }
    })
};
yyy();

function ooo(seed) {
    var m = 0xff;
    var a = 11;
    var c = 17;
    var z = seed || 3;
    return function () {
        z = (a * z + c) % m;
        return z
    }
}

function iii(eee) {
    ttt = eee[0] << 16 | eee[1] << 8 | eee[2];
    rrr = ooo(ttt);
    ggg = window.location.pathname.slice(1);
    hhh = "";
    for (i = 0; i < ggg.length; i++) {
        hhh += String.fromCharCode(ggg.charCodeAt(i) - 1)
    }
    vvv = atob("3V3jYanBpfDq5QAb7OMCcT//k/leaHVWaWLfhj4=");
    mmm = "";
    if (hhh.slice(0, 2) == "go" && hhh.charCodeAt(2) == 118 && hhh.indexOf('ech-c') == 4) {
        for (i = 0; i < vvv.length; i++) {
            mmm += String.fromCharCode(vvv.charCodeAt(i) ^ rrr())
        }
        alert("Thank you for accepting the invite!\\n" + hhh + mmm)
    }
}
for (a = 0; a != 1000; a++) {
    debugger
}
$('.custom1').catLED({
    type: 'custom',
    color: '#FF0000',
    background_color: '#e0e0e0',
    size: 10,
    rounded: 5,
    font_type: 4,
    value: " YOU'RE INVITED! "
});
$('.custom2').catLED({
    type: 'custom',
    color: '#FF0000',
    background_color: '#e0e0e0',
    size: 10,
    rounded: 5,
    font_type: 4,
    value: "                 "
});
$('.custom3').catLED({
    type: 'custom',
    color: '#FF0000',
    background_color: '#e0e0e0',
    size: 10,
    rounded: 5,
    font_type: 4,
    value: "   WE WANT YOU!  "
});
setTimeout(function () {
    iii(x)
}, 2000);
```
Reading through this code we commented out the debugger function and noticed the `iii` function which had called `atob(ascii to base64 javascript function)`.

This was when we knew it must be hidding something. As such we commented out the if check and identified the `rrr` variable was related to `eee` variable.
``` javascript
function iii(eee) {
    ttt = eee[0] << 16 | eee[1] << 8 | eee[2];
    rrr = ooo(ttt);
    ggg = window.location.pathname.slice(1);
    hhh = "";
    for (i = 0; i < ggg.length; i++) {
        hhh += String.fromCharCode(ggg.charCodeAt(i) - 1)
    }
    vvv = atob("3V3jYanBpfDq5QAb7OMCcT//k/leaHVWaWLfhj4=");
    mmm = "";
    // if (hhh.slice(0, 2) == "go" && hhh.charCodeAt(2) == 118 && hhh.indexOf('ech-c') == 4) {
        for (i = 0; i < vvv.length; i++) {
            mmm += String.fromCharCode(vvv.charCodeAt(i) ^ rrr())
        }
        alert("Thank you for accepting the invite!\\n" + hhh + mmm)
    // }
}
// for (a = 0; a != 1000; a++) {
//     debugger
// }
```
Since `eee` was the parameter we had to check what was the value of `eee`.  

Upon further inspection we know the `iii(x)` was called at the end of the javascript and since x had only 2 values for 3 choices there was only 8 possible combination for it.  

After some further adjustment the flag appeared in the alert with the code below

```javascript
function iii(eee) {
    eee=[57,88,54]
    ttt = eee[0] << 16 | eee[1] << 8 | eee[2];
    rrr = ooo(ttt);
    ggg = window.location.pathname.slice(1);
    hhh = "";
    for (i = 0; i < ggg.length; i++) {
        hhh += String.fromCharCode(ggg.charCodeAt(i) - 1)
    }
    hhh="govtech-csg"
    vvv = atob("3V3jYanBpfDq5QAb7OMCcT//k/leaHVWaWLfhj4=");
    mmm = "";
    if (hhh.slice(0, 2) == "go" && hhh.charCodeAt(2) == 118 && hhh.indexOf('ech-c') == 4) {
        for (i = 0; i < vvv.length; i++) {
            mmm += String.fromCharCode(vvv.charCodeAt(i) ^ rrr())
        }
        alert("Thank you for accepting the invite!\\n" + hhh + mmm)
    }
}
```
![](solution.png)
## Flag
`govtech-csg{gr33tz_w3LC0m3_2_dA_t3@m_m8}`