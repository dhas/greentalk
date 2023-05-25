import React, { Component } from 'react';
import { ReactComponent as Bush } from "./icons/bush.svg";
import { ReactComponent as Tree } from "./icons/tree.svg";

class Icons extends Component {

    render() {
        const { iconKey } = this.props;

        const getIcon = 
            {
                tree: <Tree className='w-100 h-100' />,
                bush: <Bush className='w-100 h-100' />,
            } 

        return (
            <>
                {getIcon[iconKey]}
            </>
        );
    };
}

export default Icons;
