package com.avisit.vijayam.managed;

import java.util.List;

public class RepeatPaginator<T> {

    private static final int DEFAULT_RECORDS_NUMBER = 10;
    private static final int DEFAULT_PAGE_INDEX = 1;

    private int recordsPerPage;
    private int recordsTotal;
    private int pageIndex;
    private int pages;
    private List<T> origModel;
    private List<T> model;

    public RepeatPaginator(List<T> model) {
    	this.origModel = model;
        initialize();
    }

	private void initialize() {
        this.recordsPerPage = DEFAULT_RECORDS_NUMBER;
        this.pageIndex = DEFAULT_PAGE_INDEX;        
        this.recordsTotal = origModel.size();

        if (recordsPerPage > 0) {
            pages = recordsPerPage <= 0 ? 1 : recordsTotal / recordsPerPage;

            if (recordsTotal % recordsPerPage > 0) {
                pages++;
            }

            if (pages == 0) {
                pages = 1;
            }
        } else {
            recordsPerPage = 1;
            pages = 1;
        }

        updateModel();
	}

    public void updateModel() {
        int fromIndex = getFirst();
        int toIndex = getFirst() + recordsPerPage;

        if(toIndex > this.recordsTotal) {
            toIndex = this.recordsTotal;
        }

        this.model = origModel.subList(fromIndex, toIndex);
    }

    public void next() {
        if(this.pageIndex < pages) {
            this.pageIndex++;
        }

        updateModel();
    }

    public void prev() {
        if(this.pageIndex > 1) {
            this.pageIndex--;
        }

        updateModel();
    }   

    public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPages() {
        return pages;
    }

    public int getFirst() {
        return (pageIndex * recordsPerPage) - recordsPerPage;
    }

    public List<T> getModel() {
        return model;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

	public void addItem(T item) {
		origModel.add(item);
		initialize();
	}

}
