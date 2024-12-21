# holiday-server-pack

This is a Gradle project to manage the server mod pack. The mod pack is distributed as a fat .jar file that can be placed into the mods folder.

### Can I submit mod X?

We aren't looking to have every mod ever, but also do not want people to feel left out. If you develop a mod that you think would be a good fit for the server pack, feel free to submit it.

If you aren't a mod developer, or a developer who thinks that a third party mod is critical for the server, please submit a PR and we can discuss it as a group. We are not trying to build a kitchen sink pack of well known popular mods, but also want to make sure that the server is fun and engaging for everyone.

If your mod does not play well with other mods or is a total conversion, it may be best to not submit it this time around.

Any mods added must have a license that allows for redistribution.

### Submitting a mod

Create a json file for your mod in /mods

- You must provide a `maven` location to a mod either on Modrinth or CurseForge.
- A `supportContact` field is required. This can either be an issue tracker or your discord name so in the event of an issue we can contact you.

```json
{
  "maven": "maven.modrinth:fabric-api:0.112.2+1.21.4",
  "supportContact": "https://github.com/FabricMC/fabric/issues"
}
```

CurseForge example: `curse.maven:fabric-api-306612:5966280`
Modrinth example: `maven.modrinth:fabric-language-kotlin:1.13.0+kotlin.2.1.0`

See the full docs here https://cursemaven.com/ and https://support.modrinth.com/en/articles/8801191-modrinth-maven

### What mods are in the pack?

A list of mods in the pack is available here. Mods in bold were made and submitted by members of our community!

- [**Alivent Messenger by A11v1r15**](https://modrinth.com/mod/alivent-messager)
- [**Armor Stand Editor by Patbox**](https://modrinth.com/mod/armorstandeditor)
- [**ClickThrough Plus by cassiancc**](https://modrinth.com/mod/clickthrough+)
- [Cloth Config API by shedaniel](https://modrinth.com/mod/cloth-config)
- [**Columns by haykam**](https://modrinth.com/mod/columns)
- [**Creeper Healing by ArkoSammy12**](https://modrinth.com/mod/creeper-healing)
- [**Creeper Confetti Plus by cassiancc**](https://modrinth.com/mod/creeperconfetti+)
- [**Do A Barrel Roll by enjarai**](https://modrinth.com/mod/do-a-barrel-roll)
- [**Ender Christmas Plus by cassiancc**](https://modrinth.com/mod/ender-christmas+)
- [**Expanded Storage by Quinn_Semele**](https://modrinth.com/mod/expanded-storage)
- [**Fabric API by modmuss50**](https://modrinth.com/mod/fabric-api)
- [**Fabric Language Kotlin by modmuss50**](https://modrinth.com/mod/fabric-language-kotlin)
- [**FlightAssistant by Octol1ttle**](https://modrinth.com/mod/flightassistant)
- [**Grind Enchantments by mschae23**](https://modrinth.com/mod/grind-enchantments)
- [**Holiday Server Mod by modmuss50**](https://github.com/modmuss50/holiday-server-pack)
- [**Instant Feedback by DrexHD**](https://modrinth.com/mod/instant-feedback)
- [MidnightLib by TeamMidnightDust](https://modrinth.com/mod/midnightlib)
- [**More Axolotl Variants API by AkashiiKun**](https://modrinth.com/mod/mavapi)
- [**More Axolotl Variants Mod by AkashiiKun**](https://modrinth.com/mod/mavm)
- [**Monkey Config by ArkoSammy12**](https://modrinth.com/mod/monkey-config)
- [owo lib by glisco03](https://modrinth.com/mod/owo-lib)
- [**Patbox's Brewery by Patbox**](https://modrinth.com/mod/brewery)
- [**Phantom Pillows by haykam**](https://modrinth.com/mod/phantom-pillows)
- [**Phantom Lucidity by haykam**](https://github.com/haykam821/Phantom-Lucidity/)
- [**PolyDecorations by Patbox**](https://modrinth.com/mod/polydecorations)
- [**Polymer by Patbox**](https://modrinth.com/mod/polymer)
- [**Pyrite by cassiancc**](https://modrinth.com/mod/pyrite)
- [**Rainbow Christmas by PoeticRainbow**](https://modrinth.com/mod/rainbow-christmas)
- [**SleepWarp (Updated) by Patbox**](https://modrinth.com/mod/sleep-warp-updated)
- [**Snow Under Trees (Fabric) by IMB11**](https://modrinth.com/mod/snow-under-trees-remastered)
- [**Text Placeholder API by Patbox**](https://modrinth.com/mod/placeholder-api)
- [**Unbreakable by sylenthuntress**](https://modrinth.com/mod/durability-rework)
- [**Universal Graves by Patbox**](https://modrinth.com/mod/universal-graves)
- [**Villager Trading Plus by cassiancc**](https://modrinth.com/mod/trading+)
- [YACL by isxander](https://modrinth.com/mod/yacl)

### Building and testing the mod pack

To build a fat jar containing all of the mods run `./gradlew :jar`. This will create a jar file in `build/libs` that can be placed in the mods folder.

To build a Modrinth modpack file run `./gradlew :mrpack`. This will create a mrpack file in `build/modrinth` that can be imported into supported launchers.

To run a test server with the mod pack run `./gradlew :test:runProductionAutoTestServer`.

### License

The code in this repository is licensed under the MIT license. See [LICENSE](LICENSE) for more information.