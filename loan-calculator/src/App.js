import React, {useState} from 'react';
import './App.css';

function InputGroup(props) {
    return (<div className="input-group">
                <label htmlFor={props.name}>
                    {props.label}
                </label>
                <input id={props.name}
                    type={props.type}
                    value={props.value}
                    onChange={props.onChange} />
            </div>)
}

function CalculationInfo(props) {
    return (<div className="calculation-info">
        <span>{props.name}<span className="result">{props.value}</span></span>
    </div>)
}

function App() {
  let [paymentCount,setPaymentCount] = useState("");
  let [amount,setAmount] = useState("");
  let [income,setIncome] = useState("");
  let [expenses,setExpenses] = useState("");
  const calculationInitialState = {
        totalCost: "",
        endDate: "",
        monthlyInstallment: ""
    };
  let [calculation,setCalculation] = useState(calculationInitialState);
  let [isError,setIsError] = useState(false);


  async function handleFormSubmit(event) {
        event.preventDefault();
        setCalculation(calculationInitialState);
        setIsError(false);
        const request = {
          paymentCount,
          amount,
          income,
          expenses
        }
        const response = await fetch("/loan", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        });
        if (response.status === 200) {
            const body = await response.json()
            setCalculation(body);
        } else {
            setIsError(true)
        }
  }

  return (
    <div className="main">
      <div className="loan-request">
        <form onSubmit={handleFormSubmit}>
            <InputGroup
                name="payment-count"
                label="Liczba rat:"
                type="number"
                value={paymentCount}
                onChange={event => setPaymentCount(event.target.value)}
            />
            <InputGroup
                name="amount"
                label="Kwota:"
                type="number"
                value={amount}
                onChange={event => setAmount(event.target.value)}
            />
            <InputGroup
                name="income"
                label="Miesięczny dochód:"
                type="number"
                value={income}
                onChange={event => setIncome(event.target.value)}
            />
            <InputGroup
                name="expenses"
                label="Miesięczne wydatki:"
                type="number"
                value={expenses}
                onChange={event => setExpenses(event.target.value)}
            />
            <button type="submit">
                Oblicz ratę
            </button>
        </form>
      </div>

      <div className="loan-response">
            <CalculationInfo name="Do spłaty całkowitej:" value={calculation.totalCost} />
            <CalculationInfo name="Kredyt do:" value={calculation.endDate} />
            <CalculationInfo name="Rata miesięczna:" value={calculation.monthlyInstallment} />
            {isError && (<div className="error">
                <p>Brak możliwości udzielenia pożyczki</p>
            </div>)}
      </div>
    </div>
  );
}

export default App;
