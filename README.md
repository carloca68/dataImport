dataImport
==========

GovHack 2013 data import utility

Code developed to import CSV files exported from the australian census worksheets into a star like database scheme.

Instructions:

Create a new MySQL database.

Set database connection info in src/main/resources/persistece.properties

Run:
	ImportValuesByDecade
	ImportValuesByYear
	
Hibernate will create the dabatase scheme and the class code will import the data from the data/new folder.
	
	