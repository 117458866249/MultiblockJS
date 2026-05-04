# MultiblockJS
This is a mod that custom multiblock machine

# How to use MultiblockJS
## Part 0 Before start
We should download these mods:  
`Rhino`,`KubeJS`,`MultiblockJS`,`ProbeJS`(This can make you code faster)  
If you want other mods' recipe, you'll need `MultiblockJS Create`,`MultiblockJS Mekaniam`, etc.

## Part 01 Register blocks
Now register a multiblock controller
```
StartupEvents.registry('block', event => {
    event.create('kubejs:controller','multiblockjs:controller')
        .notSolid()
        .tagBlock('minecraft:mineable/pickaxe')
})
```
If you want to modify its rendershape ( like geckolib )
Register like this
```
const $RenderShape = Java.loadClass("net.minecraft.world.level.block.RenderShape")
StartupEvents.registry('block', event => {
    event.create('kubejs:controller','multiblockjs:controller')
        .setRenderShape($RenderShape.XXXXX)
})
```  
Then create its appearance  
Put your model file at `kubejs/assets/mod_id/models/block`

When the multiblock doesn’t form  
The controller will use this model  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image9.png "") `xxx.json`

When it forms
The controller will use this model  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image10.png "") `xxx_formed.json`

When the multiblock in working  
The controller will use this model    
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image11.png "") `xxx_working.json`

(This mod will automatically generate several files under “blockstates” , don’t delete them dude)

---
After that , let’s register ports

### Item port
```
event.create('kubejs:andesite_item_port','multiblockjs:item_port')
    .notSolid()
    .tagBlock('minecraft:mineable/pickaxe')
    .setSize(2)
// You must write ".setSize(int)" to definition its size
// For one example , chest’s size is 27 , Large chest’s is 54
```

### Fluid port
```
event.create('kubejs:copper_fluid_port','multiblockjs:fluid_port')
    .notSolid()
    .tagBlock('minecraft:mineable/pickaxe')
    .setSize(2000)
// ".setSize(2000)" means it can fit 2000 mb fluid
```

### FE Port
```
event.create('kubejs:test_fe_port','multiblockjs:fe_port')
    .setSize(114514)
// ".setSize(114514)" means it can fit 114514 FE
```

### Create su input Port
```
event.create('kubejs:test_su_port','multiblockjs:su_input_port')
    .setRequiredStress(32)
// ".setRequiredStress(32)" means require 32*RPM su
```

### Mekanism chem Port
```
Wait for suppporting...
```
## Part 02 Multiblock Definition
Write a MultiblockJS Definition event in server script
```
MjsEvents.definition(event => {
    event.addStructure('glow_calcining_kiln', 'kubejs:controller')
    // first propety is structure name(no dumplicate)
    // second propety is controller id
})
```
Place controller at (0,0,0)
And make it facing north  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image16.png "")

Press F3+I and see its facing  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image17.png "")

Build multiblock structure around it  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image18.png "")

Record each part blocks’ blockpos in world and record them like this
```
event.addStructure('glow_calcining_kiln', 'aceii:glow_calcining_kiln_controller', [
    [1, 0, 0, 'minecraft:quartz_block'],
    [-1, 0, 0, 'minecraft:quartz_block'],
    [1, 0, 1, 'minecraft:quartz_block'],
    [0, 0, 1, 'minecraft:quartz_block'],
    [-1, 0, 1, 'minecraft:quartz_block'],
    [1, 0, 2, 'minecraft:quartz_block'],
    [0, 0, 2, 'minecraft:quartz_block'],
    [-1, 0, 2, 'minecraft:quartz_block'],
    [1, 1, 0, 'minecraft:cobbled_deepslate_wall'],
    [-1, 1, 0, 'minecraft:cobbled_deepslate_wall'],
    [1, 2, 0, 'minecraft:cobbled_deepslate_wall'],
    [-1, 2, 0, 'minecraft:cobbled_deepslate_wall'],
    [1, 1, 2, 'minecraft:cobbled_deepslate_wall'],
    [-1, 1, 2, 'minecraft:cobbled_deepslate_wall'],
    [1, 2, 2, 'minecraft:cobbled_deepslate_wall'],
    [-1, 2, 2, 'minecraft:cobbled_deepslate_wall'],
    [0, 1, 0, 'create:framed_glass'],
    [0, 2, 0, 'create:framed_glass'],
    [1, 1, 1, 'aceii:andesite_item_port'],
    [-1, 1, 1, 'aceii:andesite_item_port'],
    [1, 2, 1, 'create:andesite_casing'],
    [-1, 2, 1, 'create:andesite_casing'],
    [0, 1, 2, 'create:andesite_casing'],
    [0, 2, 2, 'create:andesite_casing'],
    [0, 3, 0, 'minecraft:cobbled_deepslate_slab'],
    [1, 3, 0, 'minecraft:cobbled_deepslate_slab'],
    [-1, 3, 0, 'minecraft:cobbled_deepslate_slab'],
    [1, 3, 1, 'minecraft:cobbled_deepslate_slab'],
    [-1, 3, 1, 'minecraft:cobbled_deepslate_slab'],
    [0, 3, 2, 'minecraft:cobbled_deepslate_slab'],
    [1, 3, 2, 'minecraft:cobbled_deepslate_slab'],
    [-1, 3, 2, 'minecraft:cobbled_deepslate_slab'],
    [0, 3, 1, 'minecraft:glowstone']
])
// Tip: don’t record the controller’s pos
```
---
Exit the save file, and then enter the save file again  
Shift & rightclick the controller block
The multiblock is available!  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image18.png "")  
↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓  
![image](https://raw.githubusercontent.com/117458866249/MultiblockJS/refs/heads/main/how2use_images/image20.png "")  

---
Tip:  
After you form your multiblock , all of its part will be invisible  
If you don’t want to make someblock invisible , register that block like this:
```
event.create('kubejs:some_block','multiblockjs:custom_part')
```
## Part 03 Recipe

Write event & Add recipe like this
```
MjsEvents.recipe(event => {
    event.addRecipe('aceii:glow_calcining_kiln_controller', 200, [
        [
            'item',
            'input',
            1, 1, 1,
            'minecraft:iron_ingot', 1
        ],
        [
            'item',
            'output',
            -1, 1, 1,
            'kubejs:wrought_iron_ingot', 2
        ]
        // Add more requirements
    ])
})
```
## Part 04 Requirements

This is a requirement and it can be used in recipe:
```
[
    'item',
    'input',
    1, 1, 1,
    'minecraft:iron_ingot', 1
]
```
I’ll show you all available requirement  
If you want to support some mods’ requirement , open a issue pls
### Item reqiurement

```
[
    'item',                   // Requirement type
    'input',                  // Input/Output
    1, 1, 1,                  // Port pos(Just like when you define your multiblock)
    'minecraft:iron_ingot', 1 // Item id and count
]
```
### Fluid requirement

```
[
    'fluid',                  // Requirement type
    'input',                  // Input/Output
    1, 1, 1,                  // Port pos
    'minecraft:lava', 2000    // Fluid id and amount
]
```
### FE requirement

```
[
    'fe',                     // Requirement type
    'input',                  // Input/Output
    1, 1, 1,                  // Port pos
    2000                      // FE amount
]
```
### Create su requirement

```
[
    'su',                     // Requirement type
    'input',                  // Input/Output
    1, 0, 0,                  // Port pos
    32                        // Speed
]
```
### Mekanism chem requirement

```
Wait for suppporting...
```
### Command requirement

```
[
    'command',                // Requirement type
    'output',                 // Only output
    1, 1, 1,                  // Execute pos
    'say ciallo~'             // Command
]
```
### Block requirement

```
[
    'block',                  // Requirement type
    'input',                  // Only input
    1, -1, 1,                 // Block's pos
    'kubejs:happy_deving'     // Block ID
]
```
## Part 05 Others

We are going to make FE , Create su , Mekanism Chemical substances requirements available

---
**Have a nice dev  
Goodbye! :>3**