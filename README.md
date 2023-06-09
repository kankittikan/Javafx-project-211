# CS211 - project211-shibatenshi (Report System Kasetsart University)
# วิธีทดสอบการ RUN
> 1. Main  
> run Main Class
> 2. javafx plugin  
> MVN Clean  
> javafx -> javafx:run

# วิธีสร้าง Jar
```
~/project211-shibatenshi> MVN Clean
~/project211-shibatenshi> MVN install
```
> file จะอยู่ใน target เป็น shade.jar

# วิธีการเปิดโปรแกรม
>- นำ folder ชื่อ data มาไว้ที่เดียวกันกับ ไฟล์ .jar (ไม่เช่นนั้นจะไม่สามารถเปิดไฟล์ได้)
>- ไฟล์ .jar พร้อม data จะอยู่ใน folder run
>- รันผ่าน command line หรือ shell ได้ โดยต้องอยู่ใน path ที่มี .jar หรือไป path `~\project211-shibatenshi\run` พิมพ์คำสั่งตามคำสั่งด้านล่าง

```
//window
~>java -jar CS211-Project-1.0-SNAPSHOT-shaded.jar
//mac-arm-64
~>java -jar CS211-Project-1.0-SNAPSHOT-shaded-mac-arm64.jar
```

# โครงสร้างของไฟล์
> project211-shibatenshi (เก็บข้อมูลเกี่ยวกับ project)
>  - data (เก็บข้อมูลทั้งไฟล์ csv และภาพ)
>     - appearance (เก็บข้อมูลของธีม)
>     - profiles (เก็บภาพของผู้ใช้งาน)
>     - reportImages (เก็บภาพประกอบของเรื่องร้องเรียน)
>  - src
>      - main
>          - java (เก็บ Java File)
>              - ku.cs
>                  - com.github.saacsos.fxrouter-1.0.0 (เป็น Package มีไว้สำหรับเปลี่ยนหน้า)
>                  - report
>                      - Animations (เก็บ Animation Class)
>                      - controllers (เก็บ Controller Class)
>                      - models (เก็บ Models Class)
>                      - services (เก็บ Service Class)
>          - resources (เก็บข้อมูลของไฟล์ css ภาพแสดงโปแกรม และ fxml)
>              - ku.cs
>                - css (เก็บไฟล์ css สำหรับธีม)
>                - images (เก็บภาพในโปรแกรม)
>  - run ( เก็บข้อมูลทดลองการเปิดโปรแกรม มีไฟล์ Jar )
>    - data (เก็บข้อมูลทั้งไฟล์ csv และภาพ)
>       - appearance (เก็บข้อมูลของธีม)
>       - profiles (เก็บภาพของผู้ใช้งาน)
>       - reportImages (เก็บภาพประกอบของเรื่องร้องเรียน)
>  - UML Diagram (เก็บภาพ uml diagram ของ Java)

# UML Diagram
## Model Class
![Model](/UML%20Diagram/Model%20Diagram.png)
## Controller Class
![Controller](/UML%20Diagram/Controller%20Diagram.png)
## Animation Class
![Animation](/UML%20Diagram/Animation%20Diagram.png)
## Service Class
![Service](/UML%20Diagram/Service%20Diagram.png)
# คู่มือการใช้งาน
>ดูคู่มือการใช้งานรายละเอียดได้ที่นี้ (เป็นไฟล์ pdf) -> [User Manual.pdf](User%20Manual.pdf)

# ตัวอย่างข้อมูลผู้ใช้ระบบ
| ประเภทผู้ใช้ระบบ | ชื่อผู้ใช้งาน (username) | รหัสผ่าน (password) |
|:-----------------|:------------------------:|:-------------------:|
| **ผู้ดูแลระบบ**  |          admin1          |        A111         |
 |                  |          admin2          |        B222         |
|                  |          admin3          |        C333         |   
| **นิสิต**        |          Tommy           |       tom1111       |
|                  |        Harryinwza        |      harry1111      |
|                  |         earth139         |         131         |
|                  |          OatKID          |       oatkid        |
|                  |          Kan575          |        K575         |
|                  |         Nisiter          |      nisit1234      |
|                  |          emmaa           |       112345        |
|                  |          Spyman          |       peanut        |
|                  |          KiYo2           |       123451        |
|                  |          Black           |       cole11        |
|                  |         warlike          |      itimwall       |
|                  |         eyye101          |        e101         |
| **เจ้าหน้าที่**  |          IndiaG          |        I1234        |
|                  |         HershelF         |        1122         |
|                  |         BrooksI          |        1111         |
|                  |          KwasiH          |        2222         |
|                  |          LinusV          |       Linu11        |
|                  |          FarahJ          |       phara11       |
|                  |          MotiM           |        3434         |
|                  |         JuliusP          |        juju         |
|                  |        MeginhardM        |        mama         |
|                  |          SonnyK          |       soso11        |
|                  |        PelagiusI         |        pee55        |
|                  |        CristianoS        |        1111         |
|                  |         NirmalA          |        ni12         |
|                  |          HamonT          |       mondev        |
|                  |         TahiraC          |        5555         |
|                  |         HermesM          |        her55        |
|                  |          AxmedL          |       arther        |
|                  |         SaoirseS         |        1234         |
|                  |          AmberF          |        am222        |

# รายละเอียดของไฟล์ CSV
## AdminDataUser.csv
| หลักที่ |        เก็บข้อมูล         | ตัวอย่าง              |
|:-------:|:-------------------------:|-----------------------|
|    1    |       ชื่อผู้ใช้งาน       | admin1                | 
|    2    |       ชื่อ-นามสกุล        | admin1                |
|    3    |         รหัสผ่าน          | A1111                 |
|    4    |      ชื่อไฟล์รูปภาพ       | UnknownProfile.jpg    |
|    5    | เวลาเข้าหน้าสู่ระบบล่าสุด | 19-10-2022 / 21:58:14 |

## CategoryReport.csv
| หลักที่ |           เก็บข้อมูล           | ตัวอย่าง          |
|:-------:|:------------------------------:|-------------------|
|    1    | ชื่อหมวดหมู่ (เท่ากับหน่วยงาน) | บริการคอมพิวเตอร์ |

## ReportData.csv
| หลักที่ |                เก็บข้อมูล                 | ตัวอย่าง              |
|:-------:|:-----------------------------------------:|-----------------------|
|    1    |      ชื่อหมวดหมู่ของเรื่องร้องเรียน       | ความปลอดภัย           | 
|    2    |       ชื่อหัวข้อของเรื่องร้องเรียน        | ผมอยากรวย             |
|    3    |         เนื้อหาของเรื่องร้องเรียน         | ขอตังหน่อยครับ        |
|    4    |    เนื้อหาเฉพาะของเรื่องร้องเรียนนั้น     | เอาจากธนาคาร          |
|    5    | ชื่อผู้ใช้งานนิสิตที่สร้างเรื่องร้องเรียน | warlike               |
|    6    |                 คะแนนโหวด                 | 0                     |
|    7    |    เวลาที่เรื่องร้องเรียนถูกสร้างขึ้น     | 19-10-2022 / 19:41:26 |
|    8    |          เจ้าหน้าที่ที่รับผิดชอบ          | ระบุไม่ได้            |
|    9    |  วิธีการจัดการหรือตอบกลับจากเจ้าหน้าที่   | ไม่มีการตอบกลับ       |
|   10    |              สถานะดำเนินการ               | รอดำเนินการ           |
|   11    |                ชื่อไฟล์ภาพ                | null                  |

## Inappropriate.csv
> จะเหมือนกันกับ ReportData.csv แต่จะเก็บไว้เหมือนเรื่องร้องเรียนนั้นไม่เหมาะสม

| หลักที่ |                เก็บข้อมูล                 | ตัวอย่าง              |
|:-------:|:-----------------------------------------:|-----------------------|
|    1    |      ชื่อหมวดหมู่ของเรื่องร้องเรียน       | ความปลอดภัย           | 
|    2    |       ชื่อหัวข้อของเรื่องร้องเรียน        | ผมอยากรวย             |
|    3    |         เนื้อหาของเรื่องร้องเรียน         | ขอตังหน่อยครับ        |
|    4    |    เนื้อหาเฉพาะของเรื่องร้องเรียนนั้น     | เอาจากธนาคาร          |
|    5    | ชื่อผู้ใช้งานนิสิตที่สร้างเรื่องร้องเรียน | warlike               |
|    6    |                 คะแนนโหวด                 | 0                     |
|    7    |    เวลาที่เรื่องร้องเรียนถูกสร้างขึ้น     | 19-10-2022 / 21:58:14 |
|    8    |          เจ้าหน้าที่ที่รับผิดชอบ          | ระบุไม่ได้            |
|    9    |  วิธีการจัดการหรือตอบกลับจากเจ้าหน้าที่   | ไม่มีการตอบกลับ       |
|   10    |              สถานะดำเนินการ               | รอดำเนินการ           |
|   11    |                ชื่อไฟล์ภาพ                | null                  |

## RequestUnbanData.csv
| หลักที่ |       เก็บข้อมูล        | ตัวอย่าง                     |
|:-------:|:-----------------------:|------------------------------|
|    1    |  ชื่อผู้ใช้งานของนิสิต  | Nisiter                      | 
|    2    |  ชื่อ-นามสกุลของนิสิต   | นิสิต นะมิตร                 |
|    3    |    รหัสผ่านของนิสิต     | nisit1234                    |
|    4    | สาเหตุที่ถูกระงับสิทธิ์ | ของผมถูกระงับสิทธิ์การใช้งาน |

## StudentDataUser.csv
| หลักที่ |        เก็บข้อมูล         | ตัวอย่าง              |
|:-------:|:-------------------------:|-----------------------|
|    1    |   ชื่อผู้ใช้งานของนิสิต   | Tommy                 | 
|    2    |   ชื่อ-นามสกุลของนิสิต    | Tom Cat               |
|    3    |     รหัสผ่านของนิสิต      | tom111                |
|    4    |  ชื่อไฟล์รูปภาพของนิสิต   | tom_be2af94.png       |
|    5    | เวลาเข้าหน้าสู่ระบบล่าสุด | 19-10-2022 / 21:28:44 |
|    6    | สถานะระงับสิทธิ์การใช้งาน | No                    |
|    7    |     วันที่โหวตล่าสุด      | No                    |

## StaffDataUser.csv
| หลักที่ |               เก็บข้อมูล               | ตัวอย่าง              |
|:-------:|:--------------------------------------:|-----------------------|
|    1    |      ชื่อผู้ใช้งานของเจ้าหน้าที่       | IndiaG                | 
|    2    |       ชื่อ-นามสกุลของเจ้าหน้าที่       | India Greger          |
|    3    |         รหัสผ่านของเจ้าหน้าที่         | I1234                 |
|    4    |      ชื่อไฟล์รูปภาพของเจ้าหน้าที่      | unnamed.jpg           |
|    5    |       เวลาเข้าหน้าสู่ระบบล่าสุด        | 19-10-2022 / 19:41:26 |
|    6    | ชื่อหน่วยงานที่รับผิดชอบของเจ้าหน้าที่ | บริการคอมพิวเตอร์     |

## Font.csv
| หลักที่ | เก็บข้อมูล | ตัวอย่าง |
|:-------:|:----------:|----------|
|    1    |  ชื่อFont  | System   | 
|    2    |   ไม่มี    | ""       |

## FontSize.csv
| หลักที่ | เก็บข้อมูล | ตัวอย่าง |
|:-------:|:----------:|----------|
|    1    |  ขนาดFont  | 15       | 
|    2    |   ไม่มี    | ""       |

## Theme.csv
| หลักที่ |     เก็บข้อมูล      | ตัวอย่าง    |
|:-------:|:-------------------:|-------------|
|    1    |      ชื่อTheme      | BrightTheme | 
|    2    | เปลี่ยนThemeตามระบบ | No System   |

# Extra Features
> **มีการเคลื่อนไหวของ gui ตาม Action ต่าง ๆ**
>- การเขียน Class Animation ขึ้นเพื่อให้ object ต่าง ๆ เคลื่อนไหวได้ เพื่อเพิ่มความสวยงาม และเพิ่มความง่ายในการใช้งาน
>
>**สามารถปรับเปลี่ยนรูปแบบของ Application ได้**
>- ผู้ใช้สามารถเลือกปรับแต่งโทนสี ขนาดฟอนต์ และฟอนต์ของตัวอักษรได้ เพื่อให้ Application สามารถใช้ได้กับความชอบของทุกคน
> 
> **เมนูจัดการเจ้าหน้าที่**
>- ผู้ดูแลระบบสามารถปรับเปลี่ยนหน่วยงานของเจ้าหน้าที่ทั้งหมดได้
>
> **เมนูจัดการหมวดหมู่เรื่องร้องเรียน**
>- ผู้ดูแลระบบสามารถเพิ่มหมวดหมู่เรื่องร้องเรียนได้ เพื่อเพิ่มความยืดหยุ่นของการใช้งาน
>- ผู้ดูแลระบบสามารถเปลี่ยนชื่อ หรือลบหมวดหมู่ได้
>
> **ลักษณะของเรื่องร้องเรียน**
>- มีการแสดงผลแบบ dropdown ของหมวดหมู่ในหน้าแจ้งเรื่องร้องเรียน
>- สามารถ upload ภาพได้ (ถ้ามี)
>
> **โหวตได้ 1 คะแนนโหวตต่อครั้ง**
>- ผู้ใช้งานสามารถโหวตเรื่องร้องเรียนต่าง ๆ ได้ 1 ครั้งต่อวัน

# ความคืบหน้าครั้งที่ 1
**นาย พีรสิษฐ์ พลอยอร่าม 6410451237**
1. Add Fxrouter in ProjectApplication.java
- method start
- method configroute
2. หน้า register
- RegisterController.java (add login button)
- register.fxml (make ui, username, password, confirmPassword, back to login)

**นาย นิสิต นะมิตร 6410451148**
1. หน้า StudentHome
- Add StudentController.java
- Add student.fxml include username, profile, logout, เรื่องร้องเรียน แบ่งเป็น เรื่องร้องเรียนในมหาลัย,
  รวมเรื่องร้องเรียนท้ังหมดจากทุกหมวดหมู่, เรื่องร้องเรียนเฉพาะผู้ใช้ระบบ.

**นายศิวกร ภาสว่าง 6410451423**
1. หน้า ผู้จัดทำโปรแกรม (about)
- ใส่รูปภาพสมาชิกกลุ่ม และปุ่มย้อนกลับสู่หน้าหลัก
- สร้าง AboutController เพื่อใส่รูปภาพสมาชิก และสร้าง method สำหรับกดย้อนกลับสู่หน้าหลัก

2. หน้า คำแนะนำการใช้โปรแกรม (How_to_use)
- ใส่หัวข้อการใช้โปรแกรมมา 2 หัวข้อ ได้แก่ การลงทะเบียนและวิธีการใช้งาน (ยังไม่มีเนื้อหาของหัวข้อนั้น เนื่องจากยังไม่ได้ออกแบบระบบภายใน)
- สร้าง HowToUseController และสร้าง method สำหรับกดย้อนกลับสู่หน้าหลัก

**นายกิตติกานต์ มากผล 6410450087**
1. หน้า login
- สร้าง Controller และออกแบบ Scene
- เพิ่ม route สำหรับปุ่มต่าง ๆ

# ความคืบหน้าครั้งที่ 2
**นาย พีรสิษฐ์ พลอยอร่าม 6410451237**
1. ระบบสมัคสมาชิก Register (ลงทะเบียนสำหรับนิสิต)
- ลงทะเบียนโดยการกรอก ชื่อผู้ใช้ ชื่อ รหัสผ่าน และยืนยันรหัสผ่าน
- มีการเช็คว่าชื่อผู้ใช้ถูกใช้ไปยังใน csv มีการกรอกรหัสผ่านและยืนยันรหัสผ่าน ถูกต้องหรือไม่

  หากกรอกรหัสผ่านและยืนยันรหัสผ่านไม่ถูกต้อง จะมีข้อความสีแดงเตือน "รหัสผ่านไม่ตรงกัน ลองอีกครั้ง"

  หากกรอกรหัสผ่านไม่ครบ จะมีข้อความสีแดงเตือน "กรุณากรอกรหัสผ่านให้ครบถ้วน"

  หากชื่อผู้ใช้ถูกใช้ไปแล้ว จะมีข้อความสีแดงเตือน "ชื่อผู้ใช้ถูกใช้งานไปแล้ว"

  หากลงทะเบียนสำเร็จจะมีข้อความ ลงทะเบียนเรียบร้อยแล้ว
- เมื่อการสมัครสมาชิกสมบูรณ์(ลงทะเบียนเรียบร้อยแล้ว) ข้อมูลจะเก็บลงใน StudentDataUser.csv

2. เพิ่มไฟล์ csv เก็บข้อมูลผู้ใช้ที่เป็นนักเรียน/นักศึกษา ชื่อ StudentDataUser โดยจะเก็บ

        username(ชื่อผู้ใช้), name(ชื่อ), password(รหัสผ่าน) ,picture(รูปโปรไฟล์) 


ในหน้า register ของนิสิต จะมีข้อมูลเข้า คือ username,name,password เท่านั้น เมื่อเก็บใน csv รูปโปรไฟล์จะถูกเซ็ตเป็น default

username(ชื่อผู้ใช้) คือชื่อที่เอาไว้ login

name(ชื่อ) คือ ชื่อที่จะแสดงในเมนูของตนเองไม่ใช่ username

picture(รูปโปรไฟล์) เมื่อสมัคร default จะเก็บเป็น UnknownProfile.jpg เปลี่ยนโดยดารอัพรูในหน้าเมนูของตนเอง เช่น

    Earth,Peerasit Ployaram,123,UnknownProfile.jpg

    loidzaza007,Loid,1111,UnknownProfile.jpg

3. ไฟล์ อ่าน/เขียน csv ชื่อ MemberListFileDataSource.java

   -อ่าน/เขียน csv ทำงานผ่าน memberlist(arraylist) ->ผ่าน member
4. services (ของ member)

   -DataSource เป็น interface ของ อ่าน/เขียน csv

   -MemberListFileDataSource ใช้อ่าน/เขียน csv DataUser มี readData,writeData
5. models

   -Member เก็บ username,name,password,picture เป็น superclass ของ StaffMember

   -MemberList เก็บ arraylist ของ Member
6. UI ของ StudentHome

   -UI StudentProfile เพิ่มรูปภาพ Username, Name,Picture, RealTimeClock,BackButton,LogoutButton

   -Css StudentProfile

**6410451423 นายศิวกร ภาสว่าง**
1. หน้าผู้จัดทำโปรแแกรม (About)
- มีการเปลี่ยนแปลง layout และสี ให้มีธีมเหมือนกัน
- เปลี่ยนจากดูข้อมูลผู้จัดทำทั้งหมด ให้แสดงข้อมูลทีละคน โดยมี Button สำหรับดูข้อมูลผู้จัดทำแต่ละคน
- เมื่อกด Button คนที่ **1** จะแสดงข้อมูลของ **นายกิตติกานต์ มากผล**
- เมื่อกด Button คนที่ **2** จะแสดงข้อมูลของ **นายนิสิต นะมิตร**
- เมื่อกด Button คนที่ **3** จะแสดงข้อมูลของ **นาพีรสิษฐ์ พลอยอร่าม**
- เมื่อกด Button คนที่ **4** จะแสดงข้อมูลของ **นายศิวกร ภาสว่าง**
2. model ของเจ้าหน้าที่ (Staff)
- การสร้างของเจ้าหน้าที่จะใช้การสืบทอดโดยเจ้าหน้าที่(StaffMember) สืบทอดจากสมาชิก (Member), เพิ่ม field ในเจ้าหน้าที่ เป็นหน่วยงานที่รับผิดชอบ (agency), ส่วนของ Constructor ของ Staff จะคล้ายกับ Constructor ของ Member แต่เพิ่ม Parameter ของหน่วยงาน และเพิ่ม method getter และ setter ของ agency สำหรับแสดงข้อมูลให้กับเจ้าหน้าที่และผู้ดูแลระบบ
- ในส่วน StaffMemberList จะมีความคล้ายกับ MemberList แต่เพียง ในการเก็บ object นั้นต้องมี type เป็น StaffMember
3. servies ของเจ้าหน้าที่ (StaffMemberListFileDataSource)
- ในส่วนของไฟล์ StaffMemberListDataSource.java จะมี field เป็น file และ directory ในส่วน method จะมี readData สำหรับอ่านไฟล์ของ StaffDataUser.csv และ writeData สำหรับเขียนลงในไฟล์ StaffDataUser.csv
4. หน้าต่างของเจ้าหน้าที่ (UI For Staff)
- ในส่วนหน้าหลักของเจ้าหน้าที่จะมีการแสดงข้อมูลของเจ้าหน้าที่ โดยเมื่อทำการเข้าสู่ระบบ จะทำการดึงข้อมูลที่มีข้อมูลผู้ใช้งานกับรหัสผ่านในไฟล์ csv ตรงกับข้อมูลที่กรอกในหน้าเข้าสู่ระบบ นอกจากนั้นมีปุ่มโปรไฟล์สำหรับเข้าหน้าต่างโปรไฟล์ของเจ้าหน้าที่ และปุ่มออกจากระบบสำหรับไปหน้าเข้าสู่ระบบ
- ในส่วนหน้าโปรไฟล์ของเจ้าหน้าที่จะแสดงข้อมูลของเจ้าหน้าที่นั้นทั้งหมด และมีปุ่มแก้ไขภาพและรหัสผ่านสำหรับเปลี่ยนแปลงรหัสผ่านและภาพของเจ้าหน้าที่นั้น

**นายนิสิต นะมิตร 6410451148**

1. ปรับปรุงหน้าหลักของนิสิต (Student Home)
- StudentHomeController
- มีการเพิ่ม Text แสดงเวลาแบบ Realtime

- เพิ่มปุ่ม ออกจากระบบ (Log-out) เพื่อกลับไปยัง หน้าเริ่มต้น (Log-in)

- เพิ่มปุ่มเข้าไปยัง หน้าโปรไฟล์ของนิสิต (StudentProfile)

- student_home.fxml
- ปรับหน้า UI ให้ดูเหมาะสมกับการใช้งาน


2. สร้างหน้าโปรไฟล์ของผู้ดูแลระบบ (Admin Profile)
- เพิ่ม AdminProfileController

- มีการเพิ่ม Text แสดงเวลาแบบ Realtime

- เพิ่มปุ่ม ออกจากระบบ (Log-out) เพื่อกลับไปยัง หน้าเริ่มต้น (Log-in)

- เพิ่มปุ่มย้อนกลับไปยัง หน้าหลักของผู้ดูแลระบบ (Admin Home)

- เพิ่ม admin_profile.fxml
- มีการทำปุ่ม ออกจากกระบบ , ย้อนกลับไปยังหน้า AdminHome , เปลี่ยนรูปภาพ และ เปลี่ยนรหัส

- ทำ Label มี ชื่อ , นามสกุล , ชื่อในระบบ และ รหัสผ่าน เพื่อใช้งานในภายภาคหน้า

- ปรับหน้า UI ให้ดูเหมาะสมกับการใช้งาน

**นายกิตติกานต์ มากผล 6410450087**
1. ปรับเปลี่ยนหน้า login ใหม่
- เพิ่มเวลา และวัน เดือน ปี ด้วย class Timeline
- เพิ่มรุปภาพที่เป็น slideshow ด้วย class Timeline
- ปรับเปลี่ยนสีของปุ่ม และเพิ่มเงื่อนไขการเปลี่ยนสี ด้วย css
- ปรับสีของ Pane ต่าง ๆ ด้วย css เพื่อจะสามารถปรับเปลี่ยนได้ง่ายภายหลัง
2. เพิ่มหน้าลืมรหัสผ่าน
- ระบุชื่อผู้ใช้ และชื่อ นามสกุล ในการเปลี่ยนรหัสผ่าน
3. สร้างหน้า Admin Home
- เพิ่มเวลา และวัน เดือน ปี ด้วย class Timeline
- เพิ่ม Image View เพื่อแสดงรูปภาพของผู้ใช้จากการรับ object จาก FXRouter
- เพิ่มปุ่มต่าง ๆ เพื่อไปยังระบบต่าง ๆ ของผุ้ดูแลระบบ

# ความคืบหน้าครั้งที่ 3
**6410451423 นายศิวกร ภาสว่าง**

1. หน้าผู้จัดทำโปรแแกรม (About)
- มีการเปลี่ยนแปลงจากการเก็บ urlImage ของแต่ละสมาชิกที่อยู่ข้างนอก method ให้ไปอยู่ข้างใน เนื่องจากปรับให้สามารถเขียน UML Diagram ได้อย่างง่ายขึ้น

2. model ของเจ้าหน้าที่ (StaffMember)
- มีการเพิ่ม parameter ของเวลาเข้าสู่ระบบใน constructor ของ StaffMember ที่มีความสัมพันธ์เป็น StaffMember is a Member.
- ใน StaffMemberList มี method findStaffMember สำหรับค้นหา

3. services ของเจ้าหน้าที่ (StaffMemberListFileDataSource)
- method ของ readData() ในการสร้าง object ได้เพิ่ม parameter ของเวลาเข้าสู่ระบบของ StaffMember
- method ของ writeData() ในการแปลงเป็น String เพื่อใส่ใน StaffDataUser.csv มีการจัดเรียงใหม่โดยเรียงลำดับดังนี้ username, name, password, picture, timelogin, agency ตามลำดับ

4. หน้าต่างของเจ้าหน้าที่ (UI Of Staff)
- ในส่วนหน้าหลักของเจ้าหน้าที่ มีการออกแบบสำหรับการจัดการของเรื่องร้องเรียนจากนิสิต โดยมี ListView สำหรับแสดงรายการของเรื่องรายงาน, มี 2 TextArea โดยส่วนที่ 1 จะแสดงเนื้อหาของเรื่องร้องเรียนและส่วนที่ 2 จะพิมพ์วิธีการจัดการของเรื่องร้องเรียนที่ได้เลือก ListView, ,มี 4 button สำหรับการจัดการเรื่องเรียนโดยเจ้าหน้าที่ แต่ทั้งหมดนี้ยังไม่มีการทำงานใดๆ
- ในส่วนหน้าเปลี่ยนรหัสผ่านของเจ้าหน้าที่มีการออกแบบที่มีน่าสนใจมากขึ้น และได้ใส่การทำงานการเปลี่ยนรหัสผ่านแล้ว

5. หน้าคู่มือการใช้งาน (HowToUse)
- มีการออกแบบเพียงเล็กน้อย และยังไม่บอกวิธีการใช้งานโปรแกรม เนื่องจากการออกแบบระบบยังอยู่ในช่วงแก้ไขการทำงานของระบบ

**6410451237 นาย พีรสิษฐ์ พลอยอร่าม**
1. หน้า StudentProfileController เพิ่ม method เปลี่ยนรหัสผ่าน(ใช้ผ่านStudentProfileChangePasswordController),เปลี่ยนรูปภาพ(ใช้ผ่าน ImageWrite,PictureFileChooser)
2. model เพิ่ม Category  ใช้กับ CategoryListFileDataSource และ csv   Report(แจ้งเรื่องร้องเรียน) ,ReportList
3. services เพิ่ม CategoryListFileDataSource, ImageWriter, PictureFileChooser, ReportListFileDataSource
4. เพิ่มcsv CategoryReport.csv , ReportData.csv
5. UI ของ student_new_report, student_all_report

**นายนิสิต นะมิตร 6410451148**

1. ปรับปรุงหน้าหลักของนิสิต (Student Home)
- StudentHomeController
- มีการเพิ่ม Text แสดงชื่อผู้ใช้ระบบ

- เพิ่มปุ่มเข้าไปยังหน้า แจ้งเรื่องร้องเรียนภายในมหาวิทยาลัย

- เพิ่มปุ่มเข้าไปยังหน้า รวมเรื่องร้องเรียนท้ังหมดจากทุกหมวดหมู่


2. สร้างหน้าโปรไฟล์ของผู้ดูแลระบบ (Admin Profile)
- AdminProfileController

- มีการเพิ่ม Text แสดงชื่อผู้ใช้ระบบ

- เพิ่มปุ่มเข้าไปยังหน้า เปลี่ยนรหัส

- เพิ่มปุ่มเปลี่ยนรูปโปรไฟล์

- เพิ่มปุ่มย้อนกลับไปยัง หน้าหลักของผู้ดูแลระบบ (Admin Home)

- admin_profile.fxml
- ปรับหน้า UI ให้ดูเหมาะสมกับการใช้งาน

3. สร้างหน้าเปลี่ยนหรัสของผู้ดูแลระบบ (Admin Profile Change Password)
- เพิ่มAdminProfileChangePasswordController

- มีการเพิ่ม Text แสดงเวลาแบบ Realtime

- เพิ่มปุ่มให้ใส่ ชื่อผู้ใช้งาน รหัสเก่ รหัสใหม่ และยืนยันหรัส

- เพิ่มปุ่มเออกจากระบบ และย้อนกลับไปยังหน้าก่อน


- เพิ่ม admin_profile_change_password.fxml
- ปรับหน้า UI ให้ดูเหมาะสมกับการใช้งาน

4. เพิ่มหน้า ของ รายชื่อผู้ใช้งาน (UserList) กับ หน้ารายงานความไม่เหมาะสม ทั้ง Controller และ UI

**6410450087 กิตติกานต์ มากผล**
1. เพิ่มหน้า ui ลงทะเบียนเจ้าหน้าที่ ที่สามารถเลือกหน่วยงานที่แสดงเป็น listview ซึ่งหน่วยงานสามารถแก้ไข หรือเพิ่ม ภายหลังได้
2. ปรับเปลี่ยนรูปแบบการเพิ่มรูป profile ของทุกส่วน เป็นการเพิ่มเข้าไปใน data/profile ซึ่งจะอยู่ภายนอกโปรแกรม
3. ย้ายตำแหน่งของ data ที่เก็บ csv รูป profile ไปไว้ที่ path ของ project เนื่องจากครั้งที่แล้ว data อยู่ใน resource ทำให้อ่านข้อมูลได้อย่างเดียว แต่เขียนข้อมูลเข้าไปไม่ได้
4. มีการเขียน Class DateTime ที่มี static method getDateTime โดยส่ง Label ที่จะให้ set ค่า วันที่และเวลาเข้าไปใน method

# ความคืบหน้าครั้งที่ 4
**6410451237 นาย พีรสิษฐ์ พลอยอร่าม**
1. หน้า แจ้งเรื่องร้องเรียนใหม่ เป็นหน้าที่เอาไว้แจ้งเรื่องร้องเรียน สามารถเพิ่มรูป หรือ ลบได้ (ผ่าน ImageWrite,PictureFileChooser)
2. หน้า รวมเเรื่องร้องเรียนจากทุกหมวหมู่ เป็นหน้ารวมเรื่องร้องเรียนที่งหมดจากผู้ใช้ที่แจ้ง 
   - โดยแบ่งเรื่องร้องเรียนออกเป็น 3 หมวด คือ รอดำเนินการ กำลังดำเนินการ และ ดำเนินการเสร็จสิ้น (Tab Pane)
   - สามารถ sort ได้ผ่าน dynamic category menu button 
   - sort ตามวันที่เวลา , คะแนนโหวคในรูปแบบต่าง
   - สามารถโหวตได้ โดยโหวตได้วันละ 1 ครั้ง
   - สามารถแจ้งความไม่เหมาะสมได้ โดยจะเก็บเป็น inappropriate.csv
3. หน้า รวมเรื่องร้องเรียนเฉพาะที่กำลัง login  เเป็นหน้าที่รวมเรื่องร้องเรียนที่ผู้ใช้ที่กำลัง login เป็นคนแจ้ง
   - โดยแบ่งเรื่องร้องเรียนออกเป็น 3 หมวด คือ รอดำเนินการ กำลังดำเนินการ และ ดำเนินการเสร็จสิ้น (Tab Pane)
   - สามารถ sort ได้ผ่าน dynamic category menu button
   - sort ตามวันที่เวลา , คะแนนโหวคในรูปแบบต่าง
4. เพิ่ม class ใน models ชื่อ SetNewTime ใช้สำหรับสลับตำแหน่งวันที่และเวลา

**6410451423 นายศิวกร ภาสว่าง**
1. หน้าหลักของเจ้าหน้าที่
    - สารมารถเลือกเรื่องร้องเรียนที่จะรับผิดชอบได้และไม่สามารถเรื่องร้องเรียนที่มีผู้รับผิดชอบก่อนหน้าได้
    - สามารถส่งวธีการจัดการที่เจ้าหน้าที่รับผิดชอบ และไม่สามารถส่งวธีการจัดการที่เจ้าหน้าที่รับผิดชอบก่อนหน้าได้
    - เลือกสถานะได้ว่าจัดการหรือยัง
    - หลังเข้าสู่ระบบ เรื่องร้องเรียนจะถูกคัดกรองให้แสดงหมวดหมู่ของเรื่องร้องเรียนตรงกับชื่อหน่วยงานของเจ้าหน้าที่นั้น
2. หน้าส่งคำร้องขอ
    - สามารถส่งคำร้องขอของนิสิตที่ถูกระงับสิทธิ์การใช้งานได้เท่านั้น
3. หน้าคำแนะนำการมใช้โปรแกรม
    - มีคำอธิบายการใช้โปรแกรมทุกระบบทั้งผู้ดูแลระบบ นิสิต และเจ้าหน้าที่
4. สร้าง Class
    - ได้สร้าง RequestUnban.java และ RequestUnbanList.java สำหรับ เก็บนิสิตที่ถูกระงับสิทธิ์และมีเหตุผลที่เกิดขึ้นดังกล่าว

**นายนิสิต นะมิตร 6410451148**

1. หน้าหลักของนิสิต ปรับปรุง UI

2. หน้าโปรไฟล์ผู้ดูแลระบบ
    - เปลื่ยนรูป และ เปลี่ยนรหัสได้

3. หน้าแจ้งเตือนความไม่เหมาะสม
    - แสดงข้อมูล และ รูป จากรายการได้
    - เพิ่มปุ่ม ลบรายการ ระงับสิทธิ์ และ คำร้องขอคืนสิทธิ์ สามารถใช้งานได้
    - เพิ่ม หน้าคำร้องขอคืนสิทธิ์
      - สามารถดูรายการ และแสดงข้อมูลได้
      - ลบรายการ และ คืนสิทธิ์ให้ผู้ใช้ สามารถใช้งานต่อได้

4. หน้ารายชื่อผู้ใช้งาน
    - แสดงข้อมูล และ รูป จากรายการได้
        - เวลาเข้าใช้งานล่าสุด
        - ชื่อผู้ใช้
        - ชื่อหน่วยงาน (เจ้าหน้าที่)

**6410450087 กิตติกานต์ มากผล**
1. หน้าจัดการหน่วยงานเจ้าหน้าที่
   - สามารถเปลี่ยนแปลงหน่วยงานเจ้าหน้าที่ได้
2. หน้าจัดการหมวดหมู่
   - สามารถเพิ่ม เปลี่ยนชื่อ ลบหมวดหมู่ได้
3. หน้าการตั้งค่า
    - ปรับเปลี่ยนรูปแบบของ Application ได้ทั้งหมด
    - เปลี่ยนโทนสีทั้งแบบสว่าง และแบบมืด
    - เปลี่ยนขนาดฟอนต์ และรูปแบบฟอนต์ต่าง ๆ ได้
4. เขียน Class Animation ให้ object ต่าง ๆ เคลื่อนไหวได้
5. เขียน Interface AppearanceConfig ที่ใช้สำหรับการปรับ Style ต่าง ๆ ของ Controller
6. เพิ่ม css ที่ใช้สำหรับการเปลี่ยนแปลงได้จากหน้าตั้งค่า