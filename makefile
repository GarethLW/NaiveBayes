JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	A4PartA.java \
	Node.java \
	LinkedList.java \
	OrderedLinkedList.java \
	FreqTable.java \
	FreqTableOrdered.java \
	RowData.java \
	Data.java \
	Stemmer.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class