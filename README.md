# <h5> Android projektit </h5> <br/>
  <h3> Ohjeet </h3> <br/>
  
  1. Asenna git default asetuksilla jotka siinä määriteltynä https://git-scm.com/downloads <br/>
  2. Paina windows näppäintä ja eti Git bash -terminaali, avaa <br/>
  3. Siinä pitäisi olla oletuksena sijainti (pwd, kertoo tämän hetkisen sijainnin) c/käyttäjä/käyttäjänimi, tee sinne uusi kansio mkdir Projects, siirry sinne cd Projects tai mihin itse nyt haluat sen projektin laittaa <br/>
  4. kirjoita git init <br/>
  5. kirjoita git pull https://github.com/TeamCurrentSource/AndroidProjects <br/>
  6. siel pitäs olla ny si
  <br/>

<h4> Api </h4>
Apin dokumentointi löytyy täältä: http://api-explorer.khanacademy.org/ <br/>
Tutki sitä ja tässä on pari esimerkki resti kutsua. <br/>
1. http://www.khanacademy.org/api/v1/topictree?kind=Topic lyö toi vaikka http://www.jsoneditoronline.org/ ja tutki sitä tarkemmin,
tuolta pitäs esim löytyä ne kaikki kategoria nimet --> children[11] -> [x] -> title <br/>
2. Noiden title nimien perusteella saadaan haettua kaikki sen katerogien videot esim. http://www.khanacademy.org/api/v1/topic/banking-and-money
