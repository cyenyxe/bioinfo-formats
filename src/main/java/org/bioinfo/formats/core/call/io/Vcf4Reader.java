package org.bioinfo.formats.core.call.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bioinfo.commons.io.utils.IOUtils;
import org.bioinfo.commons.utils.StringUtils;
import org.bioinfo.formats.commons.AbstractFormatReader;
import org.bioinfo.formats.core.call.Vcf4;
import org.bioinfo.formats.core.call.vcf4.VcfFilter;
import org.bioinfo.formats.core.call.vcf4.VcfFormat;
import org.bioinfo.formats.core.call.vcf4.VcfInfo;
import org.bioinfo.formats.core.call.vcf4.VcfRecord;
import org.bioinfo.formats.exception.FileFormatException;

public class Vcf4Reader extends AbstractFormatReader<VcfRecord> {

//	private File file;
	private BufferedReader bufferedReader;
	
	private String fileFormat;
	private Map<String, String> metaInformation;
	
	private List<VcfInfo> vcfInfos;
	private List<VcfFilter> vcfFilters;
	private List<VcfFormat> vcfFormats;
	
	private List<String> headerLine;
	
	private static final int DEFAULT_NUMBER_RECORDS = 40000;
	
	public Vcf4Reader(String filename) throws IOException, FileFormatException {
		this(new File(filename));
	}
	
	public Vcf4Reader(File file) throws IOException, FileFormatException {
		super(file);
		this.file = file;
		
		metaInformation = new LinkedHashMap<String, String>();
		
		vcfInfos = new ArrayList<VcfInfo>();
		vcfFilters = new ArrayList<VcfFilter>();
		vcfFormats = new ArrayList<VcfFormat>();
		
		bufferedReader = new BufferedReader(new FileReader(file));
		processMetaInformation();
	}
	
	private void processMetaInformation() throws IOException, FileFormatException {
		String line = "";
		String[] fields;
		BufferedReader localBufferedReader = new BufferedReader(new FileReader(file));
		while((line = localBufferedReader.readLine()) != null && line.startsWith("#")) {
//			logger.debug("line: "+line);
			if(line.startsWith("##fileformat")) {
				if(line.split("=").length > 1) {
					this.fileFormat = line.split("=")[1].trim();
				}else {
					throw new FileFormatException("");
				}
			}else {
				if(line.startsWith("##INFO")) {
					System.out.println(line);
					System.out.println(new VcfInfo(line).toString()+"\n");
				}else {
					if(line.startsWith("##FILTER")) {
						System.out.println(line);
						System.out.println(new VcfFilter(line).toString()+"\n");
					}else {
						if(line.startsWith("##FORMAT")) {
							System.out.println(line);
							System.out.println(new VcfFormat(line).toString()+"\n");
						}else {
							if(line.startsWith("#CHROM")) {
								headerLine = StringUtils.toList(line.replace("#", ""), "\t");
								System.out.println(headerLine.toString());
							}else {
								fields = line.replace("#", "").split("=", 2);
								metaInformation.put(fields[0], fields[1]);
								System.out.println(metaInformation.toString());
//								logger.warn("Warning in 'processMetaInformation': Execution cannot reach this code, line: "+line);
							}
						}
					}
					
				}
			}
		}
		localBufferedReader.close();
	}
	
	public Vcf4 parse() throws FileFormatException, IOException {
		Vcf4 vcf4 = new Vcf4();
		vcf4.setRecords(readAll());
		return vcf4;
	}
	
	@Override
	public VcfRecord read() throws FileFormatException {
		String line;
		try {
//			line = bufferedReader.readLine();
			while((line = bufferedReader.readLine()) != null && (line.trim().equals("") || line.startsWith("#"))) {
				;
			}
			if(line != null) {
//				logger.debug("line: "+line);
				String [] fields = line.split("\t");
				StringBuilder format = new StringBuilder();
				for(int i=8; i<fields.length; i++) {
					format.append(fields[i]).append("\t");
				}
				return new VcfRecord(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], format.toString().trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VcfRecord read(String regexFilter) throws FileFormatException {
//		new VcfRecord(chromosome, position, id, reference, alternate, quality, filter, info, format, samples)
		return null;
	}

	@Override
	public List<VcfRecord> read(int size) throws FileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VcfRecord> readAll() throws FileFormatException, IOException {
		List<VcfRecord> records = new ArrayList<VcfRecord>(DEFAULT_NUMBER_RECORDS);
		String line;
		String[] fields;
		bufferedReader = new BufferedReader(new FileReader(file));
		while((line = bufferedReader.readLine()) != null) {
			if(!line.startsWith("#")) {
				fields = line.split("\t");
				if(fields.length == 8) {
					records.add(new VcfRecord(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]));
				}
			}
		}
		bufferedReader.close();
		return records;
	}

	@Override
	public List<VcfRecord> readAll(String pattern) throws FileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() throws IOException, FileFormatException {
		int total = IOUtils.countLines(file);
		int comment = IOUtils.grep(file, "#·+").size();
		return total-comment;
	}
	
	@Override
	public void close() throws IOException {
		bufferedReader.close();
	}

	
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}
	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * @return the vcfInfos
	 */
	public List<VcfInfo> getInfos() {
		return vcfInfos;
	}
	/**
	 * @param vcfInfos the vcfInfos to set
	 */
	public void setInfos(List<VcfInfo> vcfInfos) {
		this.vcfInfos = vcfInfos;
	}

	/**
	 * @return the vcfFilters
	 */
	public List<VcfFilter> getFilters() {
		return vcfFilters;
	}
	/**
	 * @param vcfFilters the vcfFilters to set
	 */
	public void setFilters(List<VcfFilter> vcfFilters) {
		this.vcfFilters = vcfFilters;
	}

	/**
	 * @return the vcfFormats
	 */
	public List<VcfFormat> getFormats() {
		return vcfFormats;
	}
	/**
	 * @param vcfFormats the vcfFormats to set
	 */
	public void setFormats(List<VcfFormat> vcfFormats) {
		this.vcfFormats = vcfFormats;
	}


	
	
	
	
}
