package org.jsp.Library_Management.repository;

import org.jsp.Library_Management.dto.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
    List<BookTransaction> findByStatus(String status);
    List<BookTransaction> findByMemberId(Long memberId);
}
