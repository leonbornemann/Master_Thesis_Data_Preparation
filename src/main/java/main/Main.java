package main;

import java.io.File;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

public class Main {

	public static void main(String[] args){
		String service = "http://dbpedia.org/sparql";
		String sparqlQuery = "SELECT * WHERE {<http://dbpedia.org/resource/Barack_Obama> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?val}";

		QueryExecution qe = QueryExecutionFactory.sparqlService(service,
				sparqlQuery);
		ResultSet resultSet = qe.execSelect();
		while (resultSet.hasNext()) {
			QuerySolution next = resultSet.next();
			RDFNode rdfNode = next.get("val");
			System.out.println(rdfNode.toString());
		}
	}
}
