//import { render } from '@testing-library/react';
import React, { Component } from 'react';
import './Planes.css';
import { Button, Stack, Row, FormSelect, Tooltip, OverlayTrigger } from "react-bootstrap";
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
            sortingValue: 'entryDate',
            sortingWay: 'desc',
            planes: []
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updatePlanes = this.updatePlanes.bind(this);
        this.updatePlanesBySearch = this.updatePlanesBySearch.bind(this);
        this.updatePlanesSort = this.updatePlanesSort.bind(this);
        this.handleSortSelect = this.handleSortSelect.bind(this);
        this.handleSortClick = this.handleSortClick.bind(this);
    }
    //On load
    componentDidMount() {

        //Temp Login
        if (sessionStorage.getItem("userRol") === null) {
            let options = {
                url: process.env.REACT_APP_API_URL + "/LoginManager/test",
                method: "POST",
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'type': 3
                }
            }
            axios(options)
                .then(response => {
                    sessionStorage.setItem("userRol", response.data.userRol);
                    sessionStorage.setItem("userID", response.data.userID);
                }).catch(error => {
                    toast.error("Error al cambiar el tipo de usuario", {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                });
        }

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
                autoClose: 5000
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

    updatePlanesSort() {

        let sortingValue = this.state.sortingValue;
        let sortingWay = this.state.sortingWay;
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

    handleSortSelect = e => {
        console.log(e.target.value)
        this.setState(
            {
                sortingValue: e.target.value
            }, () => {
                this.updatePlanesSort();
            }
        );
    }

    handleSortClick() {
        let sort = this.state.sortingWay;
        if (sort === 'desc') {
            sort = 'asc';
        } else {
            sort = 'desc';
        }
        this.setState(
            {
                sortingWay: sort
            }, () => {
                this.updatePlanesSort();
            }
        );
    }

    render() {
        return (
            <div className="Planes-Container container-fluid">
                {/* Mobile */}
                <Row className='mt-2 d-lg-none'>
                    <Search updatePlanes={this.updatePlanesBySearch} />
                </Row>

                <Row className="d-lg-none">
                    <Stack direction="horizontal">
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                        <FormSelect className='w-50' onChange={this.handleSortSelect}>
                            <option selected disabled>Ordenar por...</option>
                            <option value='pk_id'>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='entryDate' defaultValue>Fecha de Ingreso</option>
                            <option value='status'>Estado</option>
                            <option value='authorName'>Autor</option>
                            <option value='type'>Tipo</option>
                        </FormSelect>
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    {(this.state.sortingWay === 'desc' ? "Descendente" : "Ascendente")}
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button id="sortButton" variant={(this.state.sortingWay === 'desc' ? "primary" : "danger")} onClick={this.handleSortClick}>
                                <i className="bi bi-arrow-down-up"></i>
                            </Button>
                        </OverlayTrigger>
                    </Stack>
                </Row>

                {/* PC */}
                <Row className="mt-2 d-none d-lg-block">
                    <Stack direction="horizontal" gap={3}>
                        <Button className="btn-sfr" id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>

                        <FormSelect className='w-50' onChange={this.handleSortSelect}>
                            <option selected disabled>Ordenar por...</option>
                            <option value='pk_id'>ID</option>
                            <option value='name'>Nombre</option>
                            <option value='entryDate' defaultValue>Fecha de Ingreso</option>
                            <option value='status'>Estado</option>
                            <option value='authorName'>Autor</option>
                            <option value='type'>Tipo</option>
                        </FormSelect>
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    {(this.state.sortingWay === 'desc' ? "Descendente" : "Ascendente")}
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button id="sortButton" variant={(this.state.sortingWay === 'desc' ? "primary" : "danger")} onClick={this.handleSortClick}>
                                <i className="bi bi-arrow-down-up"></i>
                            </Button>
                        </OverlayTrigger>

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