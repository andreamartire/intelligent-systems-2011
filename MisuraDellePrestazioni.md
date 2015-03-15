# Introduzione #

Un agente si definisce tanto più intelligente quanto più è in grado di svolgere le giuste azioni al momento giusto. Occorre quindi poter valutare l'operato dell'agente in qualsiasi momento mediante un modello di successo stabilito a priori che ne misuri le prestazioni.

In questa pagina è definita la misura delle prestazioni relativa all'agente aspirapolvere nel sistema in cui esso opera.

Si assume la seguente interpretazione del valore di prestazione:
  * "<u>buona</u>" per valori numerici alti
  * "<u>cattiva</u>" per valori numerici bassi


# Dettagli tecnici #

Si è scelto di considerare i seguenti fattori in ordine decrescente di incidenza nel valore finale della misura delle prestazioni:
  * **Massimizzazione della pulizia** (Al crescere della quantità di sporco aspirata cresce il valore della prestazione)
  * **Fattore proporzionale tra celle pulite e celle sporche**
  * **Ritorno alla base** (meno importante in quanto si assume che l'aspirapolvere debba principalmente svolgere l'obiettivo della pulizia anche a discapito dell'autonomia)

<br>
<font size='4'><strong>Formula</strong></font>

<font size='4'>2<sup>ncp/ncs</sup> - n1·db + n2·mt + n3·sp</font>


<font size='3'>Variabili</font>

<b>ncp</b> := numero celle pulite dall'aspirapolvere<br>
<b>ncs</b> := numero celle sporche correnti<br>
<b>db</b>  := distanza dalla base dell'aspirapolvere<br>
<b>mt</b>  := contatore mosse totali dell'aspirapolvere<br>
<b>sp</b>  := valore di sparsità dello sporco<br>


<font size='3'>Coefficienti Costanti</font>

<b>n1</b>, <b>n2</b>, <b>n3</b> (da definire empiricamente)