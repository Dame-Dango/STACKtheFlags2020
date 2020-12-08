'use strict';
const axios = require('axios');

const ctf_token = process.env.CTF_SCOREBOARD_TOKEN
const ctf_cookie = process.env.CTF_SCOREBOARD_COOKIE
const discord_webhook = process.env.DISCORD_WEBHOOK_URL

const scoreboardPayload = JSON.stringify({
  query: `{
    leaderboards {
        hasFrozenCompetition
        user {
            id
            username
            name
            __typename
        }
        score
        latestSolveAt
        __typename
    }
}`,
  variables: {}
});

const scoreboardPayloadConfig = {
  method: 'post',
  url: 'https://api.cat2.stf-2020.alttablabs.sg/graphql',
  headers: { 
    'Authorization': 'Bearer ' + ctf_token, 
    'Content-Type': 'application/json', 
    'Cookie': ctf_cookie
  },
  data : scoreboardPayload
};

module.exports.hello = async event => {
  return {
    statusCode: 200,
    body: JSON.stringify(
      {
        message: 'Go Serverless v1.0! Your function executed successfully!',
        input: event,
      },
      null,
      2
    ),
  };

  // Use this code if you don't use the http event with the LAMBDA-PROXY integration
  // return { message: 'Go Serverless v1.0! Your function executed successfully!', event };
};

module.exports.scoreboard = async (event, context) => {
  const scoreboardReply = (await axios(scoreboardPayloadConfig));
  // console.log(scoreboardReply.data.leade);
  const leaderboard = scoreboardReply.data.data.leaderboards;
  leaderboard.sort((a , b) => {
    if (a.score === b.score)  {
      if (a.latestSolveAt === b.latestSolveAt) return a.user.name.localeCompare(b.user.name);
      return (a.latestSolveAt === null ? 2147483647 * 1000 : a.latestSolveAt) - (b.latestSolveAt === null ? 2147483647 * 1000 : b.latestSolveAt);
    } else {
      return b.score - a.score;
    }
  });

  const currentPlacing = leaderboard.findIndex((elem) => elem.user.id === 'cki980val00d00846jmmzmldi');
  var partialDiscordPayload = {
    title: "Current Placing: " + (currentPlacing + 1),
    description: "",
    color: 65280
  };
  partialDiscordPayload.description += ("1. " + leaderboard[0].user.name + " - " + leaderboard[0].score + "pts\n...\n");
  for (var i = Math.max(1, currentPlacing - 1); i < Math.min(currentPlacing + 2, leaderboard.length); i++) {
    partialDiscordPayload.description += ((i + 1) + ". " + leaderboard[i].user.name + " - " + leaderboard[i].score + "pts\n");
  }
  partialDiscordPayload.description += "...";
  const discordPayload = {
    embeds: [partialDiscordPayload]
  };
  const discordReply = await axios.post(discord_webhook, discordPayload);
  console.log(discordReply);
  return 0;
}
