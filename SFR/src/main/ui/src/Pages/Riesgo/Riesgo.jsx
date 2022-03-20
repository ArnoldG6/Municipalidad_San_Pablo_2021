import React, { Component } from 'react';
import axios from 'axios';
//import Cookies from 'universal-cookie';
//const cookies = new Cookies();

class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            risk: null
        };
        this.refreshPage = this.refreshPage.bind(this);
    }

    componentDidMount() {
        this.refreshPage();
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_API_URL + "/RiskServlet/Retrieve/Riesgo",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'riskID': query.get('id')
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    risk: response.data
                }, () => {
                    if (this.state.risk === null || typeof this.state.risk === 'undefined') {
                        this.props.history.push('/riesgos');
                    } else {
                        console.log(this.state.risk)
                    }
                });
            }).catch(error => {
                this.props.history.push('/riesgos');
            });
    }

    render() {
        return (
            <div>
            </div>
        );
    }
}
export default Plan;
