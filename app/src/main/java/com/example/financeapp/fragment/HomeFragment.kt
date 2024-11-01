package com.example.financeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeapp.R
import com.example.financeapp.adapter.TransactionAdapter
import com.example.financeapp.databinding.FragmentHomeBinding
import com.example.financeapp.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample transactions
        val transactions = listOf(
            Transaction("Groceries", "2023-11-01", -45.20),
            Transaction("Salary", "2023-11-01", 2000.00),
            Transaction("Electricity Bill", "2023-10-28", -120.75)
        )

        // Set up RecyclerView
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionsRecyclerView.adapter = TransactionAdapter(transactions)

        // Get the current month in "yyyy-MM" format
        val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

        // Calculate balance and monthly expenses
        val totalBalance = calculateTotalBalance(transactions)
        val monthlyExpenses = calculateMonthlyExpenses(transactions, currentMonth)

        // Display calculated values in TextViews with locale-specific formatting
        binding.totalBalance.text = String.format(
            Locale.getDefault(),
            getString(R.string.total_balance),
            String.format(Locale.getDefault(), "%.2f", totalBalance)
        )

        binding.monthlyExpenses.text = String.format(
            Locale.getDefault(),
            getString(R.string.monthly_expenses),
            String.format(Locale.getDefault(), "%.2f", monthlyExpenses)
        )
    }

    private fun calculateTotalBalance(transactions: List<Transaction>): Double {
        return transactions.sumOf { it.amount }
    }

    private fun calculateMonthlyExpenses(transactions: List<Transaction>, month: String): Double {
        return transactions.filter { it.amount < 0 && it.date.startsWith(month) }
            .sumOf { it.amount }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}

