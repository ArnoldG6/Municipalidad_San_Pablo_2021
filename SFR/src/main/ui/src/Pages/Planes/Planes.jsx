//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Planes.css';
import { Button, Stack, Row } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AddPlanModal from './Components/AddPlanModal';
import axios from 'axios';
import PlansTable from './Components/PlansTable';
import Search from './Components/Search';

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
        this.updatePlanesBySearch = this.updatePlanesBySearch.bind(this);
        this.updatePlanesSort = this.updatePlanesSort.bind(this);
    }
    //On load
    componentDidMount() {
        let options = {
            url: process.env.REACT_APP_API_URL + "/PlanServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }
        //axios(options).then(toast.promise({pending: 'Cargando...'})).then(response => {
        axios(options).then(response => {
            this.setState({ planes: response.data })
        }).catch((error) => {
            console.error(error.message);
        });
    }

    updatePlanes(type) {
        if (type === "add-success") {
            toast.success("El Plan ha sido agregado satisfactoriamente!", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 10000
            });
        }
        let options = {
            url: process.env.REACT_APP_API_URL + "/PlanServlet",
            method: "GET",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options).then(response => {
            this.setState({ planes: response.data })
            //console.log(response.data)
        }).catch((error) => {
            console.error(error.message);
        });

    };

    updatePlanesBySearch(type) {
        this.setState({ planes: type });

    }

    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    updatePlanesSort(sortingValue, sortingWay) {

        let options = {
            /*cambiar el link*/
            url: process.env.REACT_APP_API_URL + "/RetrievePlans",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'sortingValue': sortingValue,
                'sortingWay': sortingWay
            }
        }
        axios(options).then(response => {
            this.setState({ planes: response.data })
        });

    };

    render() {
        return (
            <div className="Planes-Container container-fluid">
                <Row className="mt-2">
                    <Stack direction="horizontal" gap={3}>
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                        <Search updatePlanes={this.updatePlanesBySearch} />
                    </Stack>
                </Row>
                <Row>
                    <PlansTable planes={this.state.planes} updatePlanesSort={this.updatePlanesSort} />
                </Row>
                <AddPlanModal updatePlanes={this.updatePlanes} show={this.state.show} closeModal={this.closeModal} />
                <ToastContainer />
            </div>
        );
    }
};
export default Planes;