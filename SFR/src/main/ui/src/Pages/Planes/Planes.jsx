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
import Pages from '../../SharedComponents/Pagination/Pages';

class Planes extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            sortingValue: 'entryDate',
            sortingWay: 'desc',
            planes: [],
            planesView: [],
            currentPage: 1
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.updatePlanes = this.updatePlanes.bind(this);
        this.updatePlanesBySearch = this.updatePlanesBySearch.bind(this);
        this.updatePlanesSort = this.updatePlanesSort.bind(this);
        this.handleSortSelect = this.handleSortSelect.bind(this);
        this.handleSortClick = this.handleSortClick.bind(this);
        this.handlePlanesRender = this.handlePlanesRender.bind(this);
        this.updatePage = this.updatePage.bind(this);
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

        this.updatePlanesSort();
    }

    updatePlanesSort() {
        let sortingValue = this.state.sortingValue;
        let sortingWay = this.state.sortingWay;
        let options = {
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
            this.setState({
                planes: response.data,
                currentPage: 1
            }, () => {
                this.handlePlanesRender();
            });
        });
    };

    /* Pagination */
    updatePage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        }, () => {
            this.handlePlanesRender();
        });
    }

    handlePlanesRender() {
        let items = [];
        let itemAmount = process.env.REACT_APP_ITEMS_PER_PAGE_PLANES;
        let pos = (this.state.currentPage - 1) * itemAmount;
        for (let i = 0; i < itemAmount; i++) {
            let item = this.state.planes.at(pos);
            if (typeof item !== 'undefined' && item !== null) {
                items.push(item);
            }
            pos++;
        }
        this.setState({ planesView: items });
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
        this.updatePlanesSort();
    };

    /* Search */
    updatePlanesBySearch(type) {
        this.setState({
            planes: type,
            currentPage: 1
        }, () => {
            this.handlePlanesRender();
        });
    }

    /* Modal */
    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    /* Sort */
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
                        {/* Agregar Plan */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button className="btn-sfr" size="lg" onClick={this.openModal}>
                                <i className="bi bi-plus-circle"></i>
                            </Button>
                        </OverlayTrigger>

                        <FormSelect className='w-50' onChange={this.handleSortSelect} defaultValue='default'>
                            <option value='default' disabled>Ordenar por...</option>
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
                        {/* Agregar Plan */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button className="btn-sfr" size="lg" onClick={this.openModal}>
                                <i className="bi bi-plus-circle"></i>
                            </Button>
                        </OverlayTrigger>

                        <FormSelect className='w-50' onChange={this.handleSortSelect} defaultValue='default'>
                            <option value='default' disabled>Ordenar por...</option>
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
                    <PlansTable planes={this.state.planesView} updatePlanesSort={this.updatePlanesSort} />
                </Row>

                <Row>
                    <Pages
                        listLength={this.state.planes.length}
                        itemAmount={process.env.REACT_APP_ITEMS_PER_PAGE_PLANES}
                        updatePage={this.updatePage}
                        currentPage={this.state.currentPage} />
                </Row>

                <AddPlanModal updatePlanes={this.updatePlanes} show={this.state.show} closeModal={this.closeModal} />
                <ToastContainer />
            </div>
        );
    }
};
export default Planes;