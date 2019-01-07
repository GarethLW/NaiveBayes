# Naive Bayes Classifier

Compile using make

usage: java A4PartA

a.bat will also run the above code

Naïve Bayes algorithm to detect spam. It compares the combined
probability of all the tokenized attributes in an email given that the email is spam, and
compares it to non-spam(ham) using the same method. The email is classified to be of the type
with the highest probability.

I tokenized words and stemmed them using
the Porter Stemming Algorithm (Porter, 1980)
I found little difference when using trigrams for
classifying ham emails, while the ability to classify spam emails dropped significantly compared
to pure stemmed attributes. I found that creating a separate frequency table for the attributes
in the subject of the header increased the precision of classifying spam emails with no change
for ham emails. I also noted that using non-stemmed and stemmed words for the subject field
increased accuracy but non-stemmed had a greater accuracy.

The file titled A4PartA.java contains three Booleans titled USE_TRIGRAM,
USE_HEADER_DATA, and USE_ORDERED_TABLE that will use trigrams, use header data, and use
an ordered table when set to true respectively. They can be set to true or false in any
combination. Their current values are what I have found to be most optimal.

Pantel, P., & Lin, D. (1998). SpamCop: A Spam Classification & Organization Program (pp. 95-98, Tech.
No. WS-98-05). Retrieved from
http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.597.1563&rep=rep1&type=pdf

Porter, M. (2006, January). The Porter Stemming Algorithm. Retrieved November 27, 2017,
from https://tartarus.org/martin/PorterStemmer/

SpamAssassin data. (2005). Retrieved November 29, 2017, from
http://csmining.org/index.php/spam-assassin-datasets.html

