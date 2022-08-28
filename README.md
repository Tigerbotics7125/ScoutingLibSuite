# Scouting

## Sub Module READMEs
[Discord Bot](discord-bot/README.md)</p>
[Database Lib](database-lib/README.md)</p>
[TBA api](tba-api/README.md)</p>

## What is this repo for?
The goal of this repository is to provide an open source tool for scouting teams at FIRST Robotics Competitions.

This project is built on a Gradle submodule structure, allowing for new tools to be produced and
added to the same repository, here.

This project is a past-time passion project, until it becomes a vital part of any team,
whether 7125, or another; I do not intend to work on this full time.

## Installation

The discord bot relies on a discord bot token and optionally a TBA read API key.

You can get them here:
* [Discord](https://discord.com/developers/applications)
* [TBA](https://www.thebluealliance.com/account)

Create a `.env` file in the base of the repository containing your tokens as follows:
```
DISCORD_TOKEN=tokenval
TBA_TOKEN=tokenval
```
Make sure you have docker installed. You can do so [here](https://www.docker.com/products/docker-desktop/).

To start the bot and MongoDB instance, you can use the provided docker compose file as such:
``` bash
$ docker compose up
```
This will start the Bot and MongoDB instance.


## Contribution

To contribute to this repository simply:
1. Fork this repo
2. Make your changes
3. run `./gradlew spotlessApply build`
4. create a pull request!

*Please try to keep your commits atomic!*

### A special thanks to the following contributors!
[![Contributors](https://contrib.rocks/image?repo=Tigerbotics7125/Scouting)](https://github.com/Tigerbotics7125/Scouting/graphs/contributors)
