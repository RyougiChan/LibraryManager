package com.ryougi.ui;

import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.ryougi.model.Books;

@SuppressWarnings("serial")
public class SearchTableModel extends AbstractTableModel {
	
	private List<Books> dataList;
	
	public SearchTableModel() {
		dataList = Collections.emptyList();
	}

	@Override
	public int getRowCount() {
		if (dataList.size() == 0) {
			return 15;
		} else {
			return dataList.size();
		}
	}
	@Override
	public int getColumnCount() {
		return Column.values().length;
	}
	@Override
	public String getColumnName(int column) {
		return Column.values()[column].display;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (dataList.size() == 0) {
			return "";
		} else {
			Books book = dataList.get(rowIndex);
			return " "+Column.values()[columnIndex].getData(book);
		}
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	private enum Column {
		ISBN("ISBN", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getISBN();
			}
		},
		bookName("书名", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getBookName();
			}
		},
		author("著者", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getAuthor();
			}
		},
		translator("译者", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getTranslator();
			}
		},
		publisher("出版社", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getPublisher();
			}
		},
		pressYear("出版年份", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getPressYear();
			}
		},
		bookIndex("索书", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getBookIndex();
			}
		},
		state("馆藏状态", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getState();
			}
		},
		number("馆藏数目", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getNumber();
			}
		},
		source("来源", Object.class) {
			@Override
			Object getData(Books books) {
				assert books != null;
				return books.getSource();
			}
		},;

		private final String display;
		@SuppressWarnings("unused")
		private final Class<?> type;
		
		private Column(String display, Class<?> type) {
			this.display = display;
			this.type = type;
		}

		abstract Object getData(Books books);
	}

	public void setData(List<Books> bookList) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				dataList = bookList;
				fireTableDataChanged();
			}
		});
	}
}
