# Named Entity Recognition in Turkish news texts using CRF

Conditional Random Fields model for named entity recognition in Turkish news texts which is implemented in Python.

Sample input format (tab seperated) is described below: <br />

| Word        | POS           | Annotation  |
| ------------|:-------------:| -----:      |
| Tek         | Adj           | O           |
| çatı        | Noun          | O           |
| altında     | Noun          | O           |
| dokuz       | Num           | O           |
| ayrı        | Adj           | O           |
| salonda     | Noun          | O           |
| gerçekleştirilecek | Verb   | O           |
| Şenlik      | Noun          | O           |
| kapsamında	| Noun	        | O           |
|doksanın	    | Noun	        | O           |
|üzerinde	    | Noun	        | O           |
| etkinlik	  | Noun	        | O           |
| yer	        | Noun	        | O           |
| alacak	    | Verb	        | O           |

You can also use the trained model ("crf_v2.joblib") to label your test dataset. The output of the model consists of "word - predicted annotation - pos" tuple where each item is seperated with tab.

Sample output of the model is given below: <br />

| Word        | Predicted_Annotation | POS     |
| ------------|:-------------:       | -----:  |
| Istanbul    | LOCATION             | Noun    |
| yüzde       | PERCENT              | Noun    |
| 2013        | DATE                 | Num     |
| Meclis ˙    | ORGANIZATION	       | Noun    |
| lira ˙      | MONEY                |	Noun   |
| simdi       | TIME                 |  Adv    |

In order to evaluate the performance of the model, you can execute "CRF_Eval.java". It calculates CONLL F1-score, precision and recall for each annotation type using sequence alignment algorithm.

# Citing
If you use this model in an academic publication, please refer to: https://ieeexplore.ieee.org/document/8806523



