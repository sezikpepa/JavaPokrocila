***Uživatelská dokumentace***


**Význam programu**

Jelikož losování švýcarským systémem je náročná disciplína pro člověka z důvodu složitých párovacích pravidel, je lepší využít program k tomu určený. Tento program umožňuje zaregistrovat hráče na turnaj, vygenerovat jednotlivá kola švýcarským systémem a po odehrání všech kol zobrazit konečné výsledky.


**Vytvoření turnaje**

Všechny údaje o turnaji jsou ukládány do souborů ve složce, která je zvolena. V případě prvního spuštění je zobrazena stránka, kde je nutné zvolit složku, kam budou všechna data o turnaji ukládána. Je vhodné zvolit složku, kde se nebudou nacházet žádné soubory. 

**Registrace hráčů**

Poté již můžeme spravovat náš turnaj. Kliknutím na tlačítko "Show registered players" je nám zobrazena stránka, kde jsou vidět přihlášení hráči. Červeným tlačítkem u konkrétního hráče jej můžeme odstranit z turnaje (tato akce není možná po vygenerování prvního kola). Nového hráče můžeme přidat po kliknutí na tlačítko "Register new player", zde můžeme vyplnit údaje o hráči a registrovat jej.

**Vygenerování kola**

Když máme přihlášené všechny hráče, můžeme přistoupit k vygenerování zápasů prvního kola. Na stránce s registrovanými hráči klikneme na tlačítko "Show rounds", které nám zobrazí první kolo. Zobrazení je možné, pokud máme registrované alespoň tři hráče - jinak turnaj nemá smysl. Zde pomocí šipek můžeme přepínat mezi jednotlivými koly. Kolo vygenerujeme tlačítkem "Generate round". Po pár sekundách se nám zobrazí zápasy, které se v daném kole odehrají. Po jejich odehrání vybereme jejich výsledky a uložíme. Pokud máme již všechny zápasy, přesuneme se do další kola pomocí šipky. Jelikož švýcarský systém využívá pro generování i předchozí kola, je důležité generovat další kola až po uložení všechny výsledků předchozího kola. Je možné vygenerovat další kolo i bez všech výsledků, ale není to doporučováno. Též je možné v případě chyby opravit výsledky kol předchozích - losování dalších kol tím ale nebude ovlivněno.

Poznámka: počet kol je automaticky určen v závislosti na počtu hráčů.

**Zobrazení výsledků**

Kdykoliv během turnaje můžeme zobrazit výsledky turnaje. Pokud ještě nebyly odehrány všechny zápasy, jedná se o výsledky průběžné. Na tuto stránku se dostaneme pomocí tlačítka "Show results" ze stránky se zápasy. Zde vidíme hráče seřazené od nejlepšího k nejhoršímu. V prvním sloupečku vidíme počet bodů získaných v partiích (1 bod za výhru, 0.5 bodu za prohru). Dále zde vidíme pomocná hodnocení, která jsou použita v případě rovnosti bodů. Jedná se o Buchholz - součet bodů všech soupeřů, se kterými hráč hrál; Kráceny Buchholz - součet bodů všech soupeřů, se kterými hráč hrál bez bodů hráčů s nejvyšším a nejnižším počtem bodů; SonnenBorn-Berger - počet bodů všech soupeřů přenásobené výsledkem v jednotlivých partiích; Black pieces - počet zápasů, které byly odehrány s černými figurami.

**Stupně vítězů**

Na zobrazení pěkné animace tří nejlepších hráčů na stupních vítězů, klikneme na tlačítko "Show podium" na stránce se zápasy jednotlivých kol. Zde se zobrazí animace tvorby stupňů vítězů a v pomalém tempu jsou zobrazováni hráči - nejprve hráč na třetím místě, poté hráč na druhém místě a nakonec vítěz turnaje.

**Nový turnaj**

Aplikace si pamatuje složku, kde jsou uloženy údaje o turnaji. Pokud bychom chtěli vytvořit nový turnaj, vytvoříme si na disku novou složku, kterou poté v nastavení změníme na námi nově vytvořenou (tlačítko "Show settings"). Jelikož nová složka již nebude obsahovat nová data, aplikace zobrazí prázdný turnaj a my můžeme pokračovat v novém turnaji.