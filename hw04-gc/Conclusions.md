# Домашнее задание HW04-GC

## Описание
Класс `Leaky` содержит список, который поочередно сначала заполняется 1000 элементами, затем 500 случайных удаляется. Это продолжается либо 5 минут, либо до ООМ.

`Main` был запущен с параметрами `-Xmx20m -Xms20m` и со следующими garbage collectors:
- G1
- SerialGC
- ParallelGC
- ConcMarkSweepGC
- ZGC

## Результаты

| GC | Время работы приложения до ООМ, t<sub>sum</sub> | Время работы GC по данным jConsole (sec), t<sub>gc</sub> | Доля времени GC в общем времени <sup>1</sup> |
| --- | :---: | :---: | :---: |
| G1 | 04:15.344 | 0.632<sup>**2**</sup> | 0.25% |
| SerialGC | 05:03.301 | 0.587<sup>**3**</sup> | 0.19% |
| ParallelGC | 04:16.09 | 0.501<sup>**4**</sup> | 0.20% |
| ConcMarkSweepGC | 05:19.193 | 30.984<sup>**5**</sup> | 9.71% |
| ZGC | не смог стартовать - `Java heap too small` | - | - |

1) Доля времени GC в общем времени = t<sub>sum</sub> / t<sub>gc</sub> * 100%
2) G1 Young Generation = 264 ms; G1 Old Generation = 382 ms
3) Copy = 46 ms; MarkSweepCompact = 541ms
4) PS MarkSweep = 469 ms; PS Scavenge = 32 ms
5) ParNew = 56 ms; ConcurrentMarkSweep = 30928 ms

## Параметры используемой IDE
- IntelliJ IDEA 2020.2 EAP (Ultimate Edition)
- Build #IU-202.5792.28, built on June 18, 2020
- Runtime version: 11.0.7+10-b944.12 amd64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- Linux 4.15.0-101-generic
- Memory: 1237M
- Cores: 8
- Current Desktop: ubuntu:GNOME
- VM в тесте: openjdk version "13" 2019-09-17, OpenJDK Runtime Environment (build 13+33), OpenJDK 64-Bit Server VM (build 13+33, mixed mode, sharing)

## Выводы
В данном случае для приложения с интенсивным созданием новых объектов и неполным их удалением:
- GC `ConcMarkSweepGC` не подходит однозначно - т.к. слишком большая доля работы GC в общем времени.
- GC `ZGC` - может подойти, если нет жестких требований по используемой памяти. Чтобы данный тест мог запуститься требуются параметры памяти не меньше 100M. Остальные GC работают с существенно меньшем количеством памяти.
- из оставшихся трех `SerialGC` и `ParallelGC` выглядят сравнимо по доли времени GC в общем времени.
- GC `G1` немного уступает `SerialGC` и `ParallelGC`.

В итоге `SerialGC` обеспечивает наибольшее время работы до ООМ и наименьшую долю времени работы GC, и следовательно, является наиболее подходящим GC для данного приложения.


