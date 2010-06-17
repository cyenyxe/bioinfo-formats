package org.bioinfo.formats.core.call.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.formats.core.call.Vcf;
import org.bioinfo.formats.exception.FileFormatException;

public class VcfReader {

	private File file;
	private BufferedReader bufferedReader;
	
	private String fileFormat;
	private List<Info> infos;
	private List<Filter> filters;
	private List<Format> formats;
	
	
	public VcfReader(String filename) throws IOException, FileFormatException {
		this(new File(filename));
	}
	
	public VcfReader(File file) throws IOException, FileFormatException {
		this.file = file;
		FileUtils.checkFile(file);
		
		infos = new ArrayList<Info>();
		filters = new ArrayList<Filter>();
		formats = new ArrayList<Format>();
		
		bufferedReader = new BufferedReader(new FileReader(file));
		processMetaData();
	}
	
	private void processMetaData() throws IOException, FileFormatException {
		String line = "";
		while((line = bufferedReader.readLine()).startsWith("#")) {
			if(line.startsWith("##fileformat")) {
				if(line.split("=").length > 1) {
					this.fileFormat = line.split("=")[1].trim();
				}else {
					throw new FileFormatException("");
				}
			}else {
				if(line.startsWith("##INFO")) {
					
				}else {
					if(line.startsWith("##FILTER")) {
						
					}else {
						if(line.startsWith("##FORMAT")) {
							
						}else {
							if(line.startsWith("#CHROM")) {
								
							}else {
								
							}
						}
					}
					
				}
					
			}
			
		}
	}
	
	public Vcf read() throws IOException {
		String line = bufferedReader.readLine();
		String [] fields = line.split("\t");
		return new Vcf(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]);
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
	 * @return the infos
	 */
	public List<Info> getInfos() {
		return infos;
	}
	/**
	 * @param infos the infos to set
	 */
	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}

	/**
	 * @return the filters
	 */
	public List<Filter> getFilters() {
		return filters;
	}
	/**
	 * @param filters the filters to set
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * @return the formats
	 */
	public List<Format> getFormats() {
		return formats;
	}
	/**
	 * @param formats the formats to set
	 */
	public void setFormats(List<Format> formats) {
		this.formats = formats;
	}


	class Info {
		private String id;
		private String number;
		private String type;
		private String description;
		
		public Info(String id, String number, String type, String description) {
			this.id = id;
			this.number = number;
			this.type = type;
			this.description = description;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}
		/**
		 * @param number the number to set
		 */
		public void setNumber(String number) {
			this.number = number;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	class Filter {
		private String id;
		private String description;
		
		public Filter(String id, String description) {
			this.id = id;
			this.description = description;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	class Format {
		private String id;
		private String number;
		private String type;
		private String description;
		
		public Format(String id, String number, String type, String description) {
			this.id = id;
			this.number = number;
			this.type = type;
			this.description = description;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}
		/**
		 * @param number the number to set
		 */
		public void setNumber(String number) {
			this.number = number;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
