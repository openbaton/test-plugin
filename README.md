# Test plugin for Vim Driver interface

This plugin substitutes the vim-driver plugin offering a test environment to the NFVO. It doesn't call in facts the VIM so no VMs are created and you don't need any dependencies on any VIM (for instance Openstack).

It doesn't work with the Generic VNFM obviously, but only with a dummy implementation of a VNFM.
