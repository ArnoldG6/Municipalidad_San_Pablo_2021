//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Planes.css';
import { Button, Stack, Row } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AddPlanModal from './Components/AddPlanModal';
import axios from 'axios';
import PlansTable from './Components/PlansTable';

class Planes extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            planes: []
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updatePlanes = this.updatePlanes.bind(this);
    }
    //On load
    componentDidMount() {
        let options = {
            url: "http://localhost:8080/SFR/API/PlanServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        axios(options).then(response => {
            this.setState({ planes: response.data })
        });
    }

    updatePlanes(type) {
        if (type === "add-success") {
            toast.success("Funca!", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 10000
            });
        }
        let options = {
            url: "http://localhost:8080/SFR/API/PlanServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        axios(options).then(response => {
            this.setState({ planes: response.data })
        });
    };

    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    render() {
        return (
            <div className="Planes-Container container-fluid">
                <Row className="mt-2">
                    <Stack direction="horizontal" gap={3}>
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                        <form action="" className="algo ms-auto">
                            <input type="text" id="fname" name="buscadfadfr" placeholder="Buscar"></input>
                        </form>
                    </Stack>
                </Row>
                <Row>
                    <PlansTable planes={this.state.planes}/>
                </Row>
                <AddPlanModal updatePlanes={this.updatePlanes} show={this.state.show} closeModal={this.closeModal} />
                <ToastContainer />
            </div>
        );
    }
};
export default Planes;
