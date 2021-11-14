Algorithmsand DataStructures2021:
FirstPractical Assignment
Octob er12, 2021
1 Instructions
Thepracticalassignmentconsistsoftwoparts,aprogramandarep ort.
Theprogrammustb ewritten inJava,C++, CorPython3. Youcan onlyuse
librariesthatcomewithaminimalinstallationofthelanguage.Youmustwriteallco de
yourself,copyingco defromfellowstudentsortheinternetisconsideredplagiarism.^1.
Therep ortmustcontainanexplanationofyouralgorithm,ananalysisofit'scorrect-
nessandananalysisofit'sruntimecomplexity.Therep ortmusthaveatleast 2 and
atmost 10 pages.Writeyournamesontherstpageoftherep ort.

ThedeadlinefortherstpracticalassignmentisWednesdayNovemb er17,15:30.
Youshouldsubmityourco deandrep ortviaBrightspace. Youareallowedtoworkon
theassignment inpairs. Thenyoumustb eenrolled inthesamepracticalgroup on
Brightspace.Onlyoneteammemb erhastosubmittheassignment.

2 Grading
Gradeswillb edeterminedasfollows.Youmayearnupto 100 p ointsforyoursolution:

 20 p ointsfortheexplanationofyouralgorithm.
 10 p ointsforthecorrectnessanalysis.
 10 p ointsforthecomplexityanalysis.
 50 p ointsforthetestresults.Seethesubsectionontestresultsformore(imp ortant)
information.
 10 p ointsforthequalityoftheco de.
Thegradeisthetotalnumb erofp ointsdividedby10. Ifyouhavequestions,donot
hesitatetocontactJelmerFiret,jelmer.firet@ru.nl.

(^1) WewillcheckthisusingMOSS.NotethatMOSSresultscanb eviewedwithoutloggingin.Therefore
wewillnotrequireyoutoputyournameorstudentnumb erinyourco de.

3 BridgingtheGrand Canyon
TheGrandCanyonisoneofthenaturalwondersoftheworldandreceivescloseto 5
millonvisitorsp eryear.VisitorsmayeitherseetheGrandCanyonfromtheSouthRim
orfromtheNorthRim.AlthoughtheSouthRimandNorthRimareonly 10 milesapart
astheravenies,theyareseparatedbymorethan 215 milesofroad. Itisp ossibleto
hikefromonerimtotheotherthroughthecanyon,butthisisareallystrenuoushike
thattakes12-15hours.

Figure1:TheGrandCanyon.
AneccentricAmericanbillionaire,whorecentlyadoptedthenameZ-Æ-M,hiredyou
asaconsultant.Z-Æ-Mwantstoconstructasp ectacularbridgethatwillallowtouriststo
crosstheGrandCanyonmoreeasily.Thinkofthecanyonasaninnitelylongstraightline
withwidthW.Tob emoresp ecic,thecanyonisasetoftheallp ointswith 0 < y < W
inxy-plane.Z-Æ-M'sideaistobuildNhugepillarsatsp ecicplacesscatteredacrossthe
canyon.Thelo cationofthek-thpillaris(Xk, Yk).Ontopofthesepillars,Z-Æ-Mwants
toplacehugedisks. Bothpillarsanddiskswillb emadefromkryptonite,anextremely
strongandfullytransparentnewmaterial. ThereareMdierenttyp esofdisks. The
i-thtyp eofdiskshasradiusRi,anditspriceisCip erdisk. Z-Æ-Mcanbuyasmany
disksasneeded. Foreachdisk,itscentermustb eatthelo cation(Xk, Yk)ofthek-th
pillar,forsomek. Touristscanwalkfromonedisktoanotheriftheyoverlaportouch
eachother.^2 DisksmayextendpasttheSouthRim(y= 0)andtheNorthRim(y=W),
orrestonotherpillars.Touristscanonlymoveontherimsandthedisks.Whatisthe
minimumcostofallthedisksneededtomakeitp ossibletowalkfromtheSouthRimto
theNorthRim?

(^2) OneofyourinstructorshasnightmaresinwhichhehastocrossthecanyonusingZ-Æ-M'sbridge.

3.1 Input
Therstlinehas 3 space-separatedintegersN,MandW.ThenextNlinescontain 2
space-separatedintegersXkandYk.ThenalMlinescontain 2 space-separatedintegers
RiandCi.Youmayassumethefollowingconstraintsontheinputparameters:

2 ≤ W ≤ 109
0 ≤ Xk ≤ 109
1 ≤ Yk < W
1 ≤ Ri ≤ 109
1 ≤ Ci ≤ 106
Thetestcasesaredividedintothreegroups:
small: 1 ≤N, M≤ 50 ,
large: 1 ≤N, M≤ 400 ,
line: 1 ≤N, M≤ 2000 andXk= 0foreverypillar.

3.2 Output
Printtheminimumcosttomakeitp ossibletomovefromy= 0toy=W.Ifthisisnot
p ossible,printimp ossible(withoutquotes).

3.3 Examples
Sampleinput1:
11 3 13
1 9
4 2
4 6
8 7
11 4
15 4
15 10
19 4
19 5
19 10
26 1
2 1
3 100
4 10000

Sampleoutput1:

206

Sampleinput2:
4 4 100
0 10
0 30
0 55
0 80
5 4
10 4
15 6
20 10
Sampleoutput2:
24
Thesolutionofsampleinput 1 isillustratedinthediagramb elow:
Figure2:Solutionforsampleinput1.
3.4 Testingyourco de
YoucantestyourprogramusingDOMJudge(https://domjudge.science.ru.nl).
NearthestartofthesemesteryoushouldhavereceivedcredentialsfromJelmerviamail.
YoushouldensureyourprogramworksinDOMJudge,aswewilluseittojudgeyour
solution.Duringthelastdaysb eforethedeadlinethetestservergenerallyreceivesmore
submissions,thereforetakingmuchlongertojudgeyoursubmission. Thisisoneofthe
reasonsweadvicetostartearly.

Youcanalsotestyourco delo cally.WewillprovideasetofexamplesonBrightspace.
Tousetheseredirectthe.inletothestandardinputofyourprogramandcompare
theoutputwiththe.outle.

. /p r o g r a m < 1. i n > 1. o u t
diff −b q 1 .o u t 1 .a n s

3.5 Testresults
Wewillb erunningseveraltestsandyouwillgetp ointsforeverycorrectanswerwithin
thetimelimit.Ifyourco dedo esnotcompileordo esnotreadandwriteviastdinand
stdout,youwillgetzerop ointsonthetestcases. Sopleasetestyourco deonthetest
server! Ifyoupassalltestcasesonthetestserver,youcanrestassuredyouwillgeta
go o dnumb erofp ointsforthetestresults,butthisdo esnotguaranteethemaximum
numb erofp ointsforthetestresults.^3
Ifyouencounteranyissues,askforhelpearly!

(^3) However,youcanassumethisifyoursolutionworksforallvalidinputsandnisheswellwithinthe
timelimit.
