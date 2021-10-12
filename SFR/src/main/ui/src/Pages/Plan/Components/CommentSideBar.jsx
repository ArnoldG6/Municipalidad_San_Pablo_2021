import React, { Component } from 'react';
import { Nav } from "react-bootstrap";
import './CommentSideBar.css';

class CommentSideBar extends Component {
    render() {
        return (
            <div className="plan-sidebar">
                <Nav className="navbar navbar-default" role="navigation">
                    <div class="side-menu-container">
                        <ul class="nav nav-pills flex-column mb-auto">
                            
                        </ul>
                    </div>
                </Nav>
            </div>
        );
    }
};
export default CommentSideBar;
