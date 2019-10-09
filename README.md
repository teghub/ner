# Named Entity Recognition in Turkish news texts using CRF

Conditional Random Fields model for named entity recognition in Turkish news texts which is implemented in Python.

Sample input format (tab seperated) is described below: <br />

Word	POS 	Annotation <br />
Tek	Adj	O <br />
çatı	Noun	O <br />
altında	Noun	O <br />
dokuz	Num	O <br />
ayrı	Adj	O <br />
salonda	Noun	O <br />
gerçekleştirilecek	Verb	O <br />
Şenlik	Noun	O <br />
kapsamında	Noun	O <br />
doksanın	Noun	O <br />
üzerinde	Noun	O <br />
etkinlik	Noun	O <br />
yer	Noun	O <br />
alacak	Verb	O <br />

You can also use the trained model ("crf_v2.joblib") to label your test dataset. The output of the model consists of "word - predicted annotation - pos" tuple where each item is seperated with tab.

Sample output of the model is given below: <br />
Word	Predicted_Annotation	POS 	<br /> 
Istanbul ˙LOCATION	Noun <br />
yüzde ˙PERCENT	Noun <br />
2013 DATE Num <br />
Meclis ˙ORGANIZATION	Noun <br />
lira ˙MONEY	Noun <br />
simdi TIME Adv <br />

In order to evaluate the performance of the model, you can execute "CRF_Eval.java". It calculates CONLL F1-score, precision and recall for each annotation type using sequence alignment algorithm.

# Citing
If you use this model in an academic publication, please refer to: https://ieeexplore.ieee.org/document/8806523

