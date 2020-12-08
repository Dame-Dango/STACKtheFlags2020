# Scoreboard Tracker

Because we were a bit tryhard, we created a scoreboard tracker that is hosted on AWS Lambda, sending us scoreboard updates in 10 minute intervals.

The only issue is that the scoreboard page invalidates the JWT every day, so we have to change the token every day.

## Pre-requisites
Ensure you have [serverless](https://www.serverless.com/) and [nodejs](https://nodejs.org/en/) installed on your machine, with an AWS IAM user configured with AWS Lambda permissions.

## Setup
1. Initialize the folder with `npm install`.
2. Create a Discord webhook for a channel. See https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks
3. Set the following environment variables in your system (replace them with your own values):
```sh
CTF_SCOREBOARD_TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
CTF_SCOREBOARD_COOKIE=__ctfuid=
DISCORD_WEBHOOK_URL=https://discordapp.com/api/webhooks/xxx/yyy
```
4. Run the following to deploy to your AWS:
```
serverless deploy --region ap-southeast-1 --stage prod
```
