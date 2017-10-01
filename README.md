# Schmietnik java cell text midi player (working on new readme)
_**amazing**_.  
_**c h e c c**_ the [release page](https://github.com/Plasmoxy/Schmietnik/releases) for java binaries xD
_version :_ **v1.0 Sona**

### Usage  
 ```shell
 java -jar schmietnik.jar <file> <tempo> <noteLength>
 ```

* **file** - name of file with cell data ( **default = notes.txt** )
* **tempo** - tempo in BPM ( **default = 80** )
* **noteLength** - length of each note (in cells) ( **default = 1** ), its better to just stick to default

#### Examples of usage :
```
java -jar schmietnik.jar harrypotter.txt 100 2
```
 - plays **harrypotter.txt** cell file with tempo **100** and length of notes **2**
```
java -jar schmietnik.jar
```
 - plays notes.txt with default tempo and note length
### Cell file syntax

The cell files are designed to be symetrical, it is recommended to use monospace font when editing.
A song consists of at least one track, each track has at least one cell.
Each track us separated by **newline**, so one line per track.
Simultaneous note cells are separated by **spaces**.
Each **cell** consists of **3 characters** :
1. **note letter** ( CDEFGAB ), for **empty note** use **-**
2. **note shift** ( # or b ), for no shift again just use **-**
3. **note octave** -> **0 to 10**, for tenth octave use letter **A**

Example :
```
C-4 D-4 Eb4 E#6
E-6 --- Bb0
```

_Also note that the code is poorly programmed so a lot of errors may appear but its still fun lol xD_
