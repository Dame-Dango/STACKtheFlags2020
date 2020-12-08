'use strict';
var safeEval = require('safe-eval')
exports.handler = async (event) => {
    let responseBody = {
        results: "Error!"
    };
    let responseCode = 200;
    try {
        if (event.body) {
            let body = JSON.parse(event.body);
            // Secret Formula
            let context = {person: {code: 3.141592653589793238462}};
            let taxRebate = safeEval((new Buffer(body.age, 'base64')).toString('ascii') + " + " + (new Buffer(body.salary, 'base64')).toString('ascii') + " * person.code",context);
            responseBody = {
                    results: taxRebate
            };
        }
    } catch (err) {
        console.error(err)
        responseCode = 500;
    }
    let response = {
        statusCode: responseCode,
        headers: {
            "x-custom-header" : "tax-rebate-checker"
        },
        body: JSON.stringify(responseBody)
    };
    return response;
};