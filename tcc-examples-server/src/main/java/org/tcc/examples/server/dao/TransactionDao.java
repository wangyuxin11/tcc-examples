package org.tcc.examples.server.dao;

import org.tcc.examples.server.dto.PageDto;
import org.tcc.examples.server.vo.TransactionVo;

import java.util.List;

public interface TransactionDao {

    public List<TransactionVo> findTransactions(Integer pageNum, int pageSize);

    public Integer countOfFindTransactions();

    public void resetRetryCount(String globalTxId, String branchQualifier);

    public void delete(String globalTxId, String branchQualifier);

    public void confirm(String globalTxId, String branchQualifier);

    public void cancel(String globalTxId, String branchQualifier);

    public String getDomain();

    public PageDto<TransactionVo> findTransactionPageDto(Integer pageNum, int pageSize);
}

