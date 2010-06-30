package org.bioinfo.formats.io.parser.uniprot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bioinfo.commons.io.TextFileReader;
import org.bioinfo.commons.io.TextFileWriter;
import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.commons.utils.ListUtils;
import org.bioinfo.commons.utils.StringUtils;
import org.bioinfo.formats.parser.uniprot.UniprotParser;
import org.bioinfo.formats.parser.uniprot.v135jaxb.CommentType;
import org.bioinfo.formats.parser.uniprot.v135jaxb.Entry;
import org.bioinfo.formats.parser.uniprot.v135jaxb.FeatureType;
import org.bioinfo.formats.parser.uniprot.v135jaxb.Uniprot;
import org.junit.Test;

public class UniprotParserTest {

	@Test
	public void getNamesAndFunctionFromUniprot() {

		String chunksDirname = "/mnt/commons/formats/uniprot/chunks/";
		String outFilename = "/mnt/commons/formats/uniprot/names_and_function_uniprot.txt";

		UniprotParser up = new UniprotParser();

		try {
			String name, proteinName, geneName, function;

			TextFileWriter writer = new TextFileWriter(outFilename);
			writer.writeLine("#accession\tname\tprotein name\tgene name\tfunction");
			Uniprot uniprot = null;

			File[] xmlFiles = FileUtils.listFiles(new File(chunksDirname), ".+.xml", true);

			//			List<File> xmlFiles = new ArrayList<File>();
			//			xmlFiles.add(new File("/home/jtarraga/bioinfo/uniprot/chunks/chunk_entry_007.xml"));
			for(File file: xmlFiles) {

				System.out.println("searching in " + file.getAbsolutePath() + "...");

				uniprot = (Uniprot) up.loadXMLInfo(file.getAbsolutePath());
				for(Entry entry: uniprot.getEntry()) {
					//System.out.println("accession = " + entry.getAccession().get(0));
					name = "";
					proteinName = "";
					geneName = "";
					function = "";

					if (entry.getName()!=null && entry.getName().get(0)!=null && entry.getName().get(0).length()>0) {
						name = entry.getName().get(0);
					}

					if (entry.getProtein()!=null) {
						if (entry.getProtein().getRecommendedName()!=null) {
							proteinName = entry.getProtein().getRecommendedName().getFullName().getValue();
						}
					}

					if (entry.getGene()!=null && entry.getGene().size()>0 && entry.getGene().get(0)!=null) {
						geneName = entry.getGene().get(0).getName().get(0).getValue();
					}

					for(CommentType commentType: entry.getComment()) {
						if (commentType.getType().equalsIgnoreCase("function")) {
							function = commentType.getText().getValue();
							break;
						}
					}

					for(String accession: entry.getAccession()) {
						writer.writeLine(accession + "\t" + name + "\t" + proteinName + "\t" + geneName + "\t" + function);
					}

//					if ("P31946".equalsIgnoreCase(entry.getAccession().get(0))) {
//						System.out.println("fuction = " + function);
//						
//						writer.close();
//						Runtime.getRuntime().exit(1);
//					}

				}				
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getSnpFromUniprot() {

		String chunksDirname = "/mnt/commons/formats/uniprot/chunks/";
		String outFilename = "/mnt/commons/formats/uniprot/snp_in_uniprot.txt";

		UniprotParser up = new UniprotParser();

		try {
			String description = null;
			String[] ids = null;
			List<String> values = null;
			List<String> descs = new ArrayList<String>();
			List<String> snpIds = new ArrayList<String>();

			TextFileWriter writer = new TextFileWriter(outFilename);
			writer.writeLine("#snp\tsource\tsource ID\tsource name\tdescription");
			Uniprot uniprot = null;

			File[] xmlFiles = FileUtils.listFiles(new File(chunksDirname), ".+.xml", true);

			//			List<File> xmlFiles = new ArrayList<File>();
			//			xmlFiles.add(new File("/home/jtarraga/bioinfo/uniprot/chunks/chunk_entry_007.xml"));
			for(File file: xmlFiles) {

				System.out.println("searching in " + file.getAbsolutePath() + "...");

				uniprot = (Uniprot) up.loadXMLInfo(file.getAbsolutePath());
				for(Entry entry: uniprot.getEntry()) {

					List<FeatureType> features = entry.getFeature();
					if (features!=null && features.size()>0) {
						for(FeatureType f: features) {
							if (f.getDescription()!=null && f.getDescription().contains("dbSNP")) {

								snpIds.clear();
								descs.clear();

								values = StringUtils.toList(f.getDescription(), ";");
								for(String value: values) {
									if (value.contains("dbSNP")) {
										ids = value.split("dbSNP:");
										//										System.out.println("value = " + value);
										for(String id: ids) {
											if (id.startsWith("rs")) {
												id = id.split(" ")[0].replace(")", "");
												//												System.out.println("id: " + id);
												snpIds.add(id);
											}
										}
									} else {
										descs.add(value);
									}
								}

								if (snpIds!=null && snpIds.size()>0) {
									description = ListUtils.toString(descs, ";");
									if (description.startsWith("(")) description = description.substring(1);
									for(String snpId: snpIds) {
										writer.writeLine(snpId + "\tuniprot\t" + entry.getAccession().get(0) + "\t" + entry.getName().get(0) + "\t" + description);
									}
								}
							}
						}
					}
				}				
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testLoadOmimInfo() {
		int count = 1;

		String inFilename = "/mnt/commons/formats/omim/omim.txt";
		String outFilename = "/mnt/commons/formats/omim/snp_in_omim.txt";

		try {
			boolean endOfRecord = true;
			String line;

			TextFileWriter writer = new TextFileWriter(outFilename);
			writer.writeLine("#snp\tsource\tsource ID\tsource name\tdescription");

			TextFileReader reader = new TextFileReader(inFilename);

			String id = null, description = null;
			List<String> snpIds = new ArrayList<String>();
			List<String> snpLines = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();

			while ((line=reader.readLine())!=null) {

				//System.out.println("line = " + line);

				if (line.startsWith("*RECORD*")) {

					id = null;
					description = null;
					snpLines.clear();
					snpIds.clear();
					endOfRecord = false;
					sb.setLength(0);

					while(!endOfRecord && line!=null) {
						line = reader.readLine();

						//System.out.println("line = " + line);

						if (line.startsWith("*FIELD* NO")) {
							id = reader.readLine();
							System.out.println("parsing record " + id + " (count " + (count++) + ")....");
							//System.out.println("id = " + id);
						} else if (line.startsWith("*FIELD* TI")) {
							line = reader.readLine();
							description = line.substring(line.indexOf(" ")+1);
							//System.out.println("description = " + description);
						} else if (line.startsWith("*RECORD*") || line.startsWith("*THEEND*")) {
							endOfRecord = true;
						} else {
							sb.append(line).append(" ");
						}
					}
					if (sb.length()>0) {
						snpIds = getSnpIds(sb);
						for(String snpId: snpIds) {
							writer.writeLine(snpId + "\tomim\t" + id + "\t\t" + description);							
						}
						//						if (snpLines.size()>0) {
						//						System.out.println(id + "\t" + description);
						//						System.out.println(ListUtils.toString(snpLines, "\n"));
						//						Runtime.getRuntime().exit(0);
					}
				}
			}

			writer.close();


		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	public List<String> getSnpIds(StringBuilder line) {
		List<String> res = new ArrayList<String>();
		String snpId;

		//System.out.println("line = " + line);

		boolean endOfId;
		int endLine, beginIndex;

		beginIndex = 0;
		endLine = line.length();

		while (beginIndex < endLine) {
			beginIndex = line.indexOf("dbSNP rs", beginIndex);
			if (beginIndex<0) {
				break;
			}
			System.out.println("beginIndex = " + beginIndex);
			endOfId = false;
			snpId = "rs";
			beginIndex += 8;
			while(!endOfId) {
				snpId += line.charAt(beginIndex);
				beginIndex++;
				endOfId = !isDigit(line.charAt(beginIndex));
			}
			//System.out.println("endIndex = " + endIndex);
			//snpId = line.substring(beginIndex + 6, endIndex); //, line.indexOf(" ", beginIndex + 7));
			//beginIndex = endIndex + 1;

			res.add(snpId);
			//System.out.println("snpId = (" + snpId + ")" + beginIndex + ", " + endLine);
			//Runtime.getRuntime().exit(0);
		}

		//		if (ListUtils.unique(res).size()>1) {
		//			System.out.println("snpIds = (" + ListUtils.toString(res, ",") + ")");
		//			Runtime.getRuntime().exit(0);
		//		}

		return ListUtils.unique(res);
	}

	private boolean isDigit(char c) {
		if (c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9') {
			return true;
		}
		return false;
	}
}