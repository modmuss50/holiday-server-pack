# holiday-server-pack

This is a Gradle project to manage the server mod pack. The mod pack is distributed as a fat .jar file that can be placed into the mods folder.

### Can I submit mod X?

We aren't looking to have every mod ever, but also do not want people to feel left out. If you develop a mod that you think would be a good fit for the server pack, feel free to submit it.

If you aren't a mod developer, or a developer who thinks that a third party mod is critical for the server, please submit a PR and we can discuss it as a group. We are not trying to build a kitchen sink pack of well known popular mods, but also want to make sure that the server is fun and engaging for everyone.

If your mod does not play well with other mods or is a total conversion, it may be best to not submit it this time around.

Please refrain from adding client side only mods, players are free to add these mods to their own clients.

Any mods added must have a license that allows for redistribution.

### Submitting a mod

Create a json file for your mod in /mods

- You must provide a `maven` location to a mod either on Modrinth or CurseForge.
- A `supportContact` field is required. This can either be an issue tracker or your discord name so in the event of an issue we can contact you.

```json
{
  "maven": "curse.maven:fabric-api-306612:5966280",
  "supportContact": "https://github.com/FabricMC/fabric/issues"
}
```

CurseForge example: `curse.maven:fabric-api-306612:5966280`
Modrinth example: `maven.modrinth:fabric-language-kotlin:1.13.0+kotlin.2.1.0`

See the full docs here https://cursemaven.com/ and https://support.modrinth.com/en/articles/8801191-modrinth-maven

### Building and testing the mod pack

To build a fat jar containing all of the mods run `./gradlew :jar`. This will create a jar file in `build/libs` that can be placed in the mods folder.

To build a Modrinth modpack file run `./gradlew :mrpack`. This will create a mrpack file in `build/modrinth` that can be imported into supported launchers.

To run a test server with the mod pack run `./gradlew :test:runProductionAutoTestServer`.