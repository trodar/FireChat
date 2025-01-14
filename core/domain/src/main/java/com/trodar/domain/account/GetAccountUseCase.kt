package com.trodar.domain.account

import com.trodar.data.repository.AccountRepository
import com.trodar.model.Account
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    operator fun invoke(
        accountId: String
    ) : Flow<Account> = accountRepository.getAccount(accountId)
}