package edu.aau.projects.volunteerapp.model;

public class Admin implements CashFundHolder{
    private int adminId;
    private User user;

    private CashFund cashFund;
    public Admin() {
        user = new User();
        cashFund = new CashFund();
    }

    public Admin(User user) {
        this.user = user;
        cashFund = new CashFund();
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CashFund getCashFund() {
        return cashFund;
    }

    public void createCashFund() {
        this.cashFund = getCashFundData();
    }

    @Override
    public CashFund getCashFundData() {
        CashFund cashFund = new CashFund();
        cashFund.setBalance(0.0);
        cashFund.setFundName(this.getUser().getName().concat(" Cash Fund"));
        cashFund.setOwnerType("Individual");
        cashFund.setOwnerId(getAdminId());
        return cashFund;
    }
}
