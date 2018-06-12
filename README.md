# Avata-Book-API using SpringBoot

Epoch: a epoch is defined by a api call, every 4 seconds
N is a counter on epochs, where N=1 is the epoch (currentTime, currentTime - 4secs), N = 10 is the epoch (currentTime - 36secs, currentTime - 40secs)

A scheduled task polls the-book api every 4 seconds, timestamps the response and saves it in memory (Max capacity set to 50 timestamps)
As new responses arrive, the responses for the older timestamps are removed limiting the in-memory map to its capacity

API end points

1. To get the top X books based on their review ratings for the epoch of counter N <br/>
/api/book/{epoch}/{topx}

2. To get the title similarities of the top X books for a given N <br/>
/api/book/similarity/{epoch}/{topx}

3. To get the top 'a' keywords in the summary of the top X books for given N <br/>
/api/book/keyword/{epoch}/{topx}/{noofkeywords}

Run AssestmentApplication.java and view the results in your browser on http://localhost:8080/

eg: http://localhost:8080/api/book/keyword/50/50/50
	http://localhost:8080/api/book/similarity/2/10
	http://localhost:8080/api/book/29/40

Top keywords are computed using Term Frequency-Inverse Document Frequency values for each word in the summary
Title similarities scores computed as Jaccard Similarity[title]*100 


