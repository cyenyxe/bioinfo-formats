package org.bioinfo.formats.core.variant.vcf4.filter;

import org.bioinfo.formats.core.variant.vcf4.VcfRecord;

public interface VcfGenericFilter {

	public boolean filter(VcfRecord vcfRecord);
	
}
