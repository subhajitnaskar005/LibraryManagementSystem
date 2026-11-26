package org.jsp.Library_Management.dto;

public class DashboardStatsDTO {
    private long totalBooks;
    private long totalMembers;
    private long totalIssuedBooks;
    private long totalOverdueBooks;
    private long totalWaitlisted;
    private double totalFineCollected;

    public DashboardStatsDTO(long totalBooks, long totalMembers, long totalIssuedBooks,
                              long totalOverdueBooks, long totalWaitlisted, double totalFineCollected) {
        this.totalBooks = totalBooks;
        this.totalMembers = totalMembers;
        this.totalIssuedBooks = totalIssuedBooks;
        this.totalOverdueBooks = totalOverdueBooks;
        this.totalWaitlisted = totalWaitlisted;
        this.totalFineCollected = totalFineCollected;
    }

    public long getTotalBooks() { return totalBooks; }
    public long getTotalMembers() { return totalMembers; }
    public long getTotalIssuedBooks() { return totalIssuedBooks; }
    public long getTotalOverdueBooks() { return totalOverdueBooks; }
    public long getTotalWaitlisted() { return totalWaitlisted; }
    public double getTotalFineCollected() { return totalFineCollected; }
}