package FactoryMethodPattern_Example;

// Define the Document interface
interface Document {
    void open();
    void close();
    void save();
}

// Implement the concrete classes for each document type
class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Word document...");
    }

    @Override
    public void close() {
        System.out.println("Closing Word document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Word document...");
    }
}

class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document...");
    }
}

class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening Excel document...");
    }

    @Override
    public void close() {
        System.out.println("Closing Excel document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Excel document...");
    }
}

// Define the abstract factory class
abstract class DocumentFactory {
    public abstract Document createDocument();
}

// Implement the concrete factory classes for each document type
class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}

// Test the Factory Method Pattern implementation
public class FactoryMethodPatternExample {
    public static void main(String[] args) {
        // Create a Word document
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document wordDocument = wordFactory.createDocument();
        wordDocument.open();
        wordDocument.save();
        wordDocument.close();

        // Create a PDF document
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdfDocument = pdfFactory.createDocument();
        pdfDocument.open();
        pdfDocument.save();
        pdfDocument.close();

        // Create an Excel document
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDocument = excelFactory.createDocument();
        excelDocument.open();
        excelDocument.save();
        excelDocument.close();
    }
}

