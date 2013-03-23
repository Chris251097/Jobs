/**
 * Jobs Plugin for Bukkit
 * Copyright (C) 2011 Zak Ford <zak.j.ford@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.zford.jobs.bukkit.tasks;

import me.zford.jobs.bukkit.economy.BufferedEconomy;
import me.zford.jobs.economy.BufferedPayment;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class BufferedPaymentTask implements Runnable {
    private BufferedEconomy bufferedEconomy;
    private Economy economy;
    private BufferedPayment payment;
    public BufferedPaymentTask(BufferedEconomy bufferedEconomy, Economy economy, BufferedPayment payment) {
        this.bufferedEconomy =  bufferedEconomy;
        this.economy = economy;
        this.payment = payment;
    }
    @Override
    public void run() {
        if (payment.getAmount() > 0) {
            economy.depositPlayer(payment.getPlayerName(), payment.getAmount());
        } else {
            EconomyResponse response = economy.withdrawPlayer(payment.getPlayerName(), -payment.getAmount());
            if (response.type == EconomyResponse.ResponseType.FAILURE) {
                bufferedEconomy.pay(payment);
            }
        }
    }
}
